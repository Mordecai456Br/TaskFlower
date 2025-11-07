// Seleciona todos os elementos com a classe '.kanban-card' e adiciona eventos a cada um deles
document.querySelectorAll('.kanban-card').forEach(card => {
    card.addEventListener('dragstart', e => {
        e.currentTarget.classList.add('dragging');
    });

    card.addEventListener('dragend', e => {
        e.currentTarget.classList.remove('dragging');
    });
});

// Seleciona todos os elementos com a classe '.kanban-cards' (as colunas)
document.querySelectorAll('.kanban-cards').forEach(column => {
    column.addEventListener('dragover', e => {
        e.preventDefault();
        e.currentTarget.classList.add('cards-hover');
    });

    column.addEventListener('dragleave', e => {
        e.currentTarget.classList.remove('cards-hover');
    });

    column.addEventListener('drop', e => {
        e.currentTarget.classList.remove('cards-hover');
        const dragCard = document.querySelector('.kanban-card.dragging');
        e.currentTarget.appendChild(dragCard);
    });
});

let selectedColumn = null;
let editingCard = null; // <-- novo: guarda o card que está sendo editado

// Elementos do modal
const modal = document.getElementById("taskModal");
const taskForm = document.getElementById("taskForm");
const closeModalBtn = document.querySelector(".close-modal");
const cancelBtn = document.querySelector(".btn-cancel");

// Adicionar event listeners para os botões de adicionar card
document.querySelectorAll(".add-card").forEach((button) => {
  button.addEventListener("click", function () {
    selectedColumn = this.closest(".kanban-column");
    editingCard = null; // novo card, não edição
    openModal();
  });
});

function openModal(taskData = null) {
  modal.classList.add("active");

  const today = new Date().toISOString().split("T")[0];
  document.getElementById("taskDate").value = today;

  // Se for edição, preencher os campos
  if (taskData) {
    document.getElementById("taskTitle").value = taskData.title;
    document.getElementById("taskDescription").value = taskData.description;
    document.getElementById("taskPriority").value = taskData.priority;
    document.getElementById("taskProject").value = taskData.project;
    document.getElementById("taskDate").value = taskData.rawDate; // data sem formatar
    document.getElementById("taskAssignee").value = taskData.assignee;
  }
}

function closeModal() {
  modal.classList.remove("active");
  taskForm.reset();
  selectedColumn = null;
  editingCard = null;
}

closeModalBtn.addEventListener("click", closeModal);
cancelBtn.addEventListener("click", closeModal);

modal.addEventListener("click", (e) => {
  if (e.target === modal) closeModal();
});

document.addEventListener("keydown", (e) => {
  if (e.key === "Escape" && modal.classList.contains("active")) closeModal();
});

function formatDate(dateString) {
  const date = new Date(dateString);
  const day = String(date.getDate()).padStart(2, "0");
  const month = String(date.getMonth() + 1).padStart(2, "0");
  const year = date.getFullYear();
  return `${day}/${month}/${year}`;
}

// Funções de drag
function handleDragStart(e) {
  e.dataTransfer.effectAllowed = "move";
  e.dataTransfer.setData("text/plain", null);
  e.target.classList.add("dragging");
}

function handleDragEnd(e) {
  e.target.classList.remove("dragging");
}

function handleDragOver(e) {
  e.preventDefault();
  const container = e.currentTarget.querySelector(".kanban-cards");
  const dragging = document.querySelector(".dragging");
  const afterElement = getDragAfterElement(container, e.clientY);

  if (!dragging) return;

  if (afterElement == null) {
    container.appendChild(dragging);
  } else {
    container.insertBefore(dragging, afterElement);
  }
}

function getDragAfterElement(container, y) {
  const draggableElements = [...container.querySelectorAll(".kanban-card:not(.dragging)")];

  return draggableElements.reduce((closest, child) => {
    const box = child.getBoundingClientRect();
    const offset = y - box.top - box.height / 2;
    if (offset < 0 && offset > closest.offset) {
      return { offset: offset, element: child };
    } else {
      return closest;
    }
  }, { offset: Number.NEGATIVE_INFINITY }).element;
}

document.querySelectorAll(".kanban-column").forEach(column => {
  column.addEventListener("dragover", handleDragOver);
});

// Função para criar nova task
function createTaskCard(taskData) {
  const card = document.createElement("div");
  card.className = "kanban-card";
  card.draggable = true;

  card.innerHTML = `
        <div class="todobody">
            <div class="status"><div class="statusnormal"></div></div>
            <div class="todo-content">
                <div class="content">
                    <div class="alltags">
                        <div class="prioridade"><div class="${taskData.priority}">${taskData.priority}</div></div>
                        <div class="project-tag"><div class="high">⚙️ ${taskData.project}</div></div>
                        <div class="tags"></div>
                        <div class="div">${taskData.date}</div>
                    </div>
                    <div class="body">
                        <div class="header"><div class="registro-jwt">${taskData.title}</div></div>
                        <div class="implementar-a-funo">${taskData.description}</div>
                    </div>
                </div>
            </div>
        </div>
        <div class="persons">
            <div class="personinproject"><div class="gabrielbegodex">${taskData.assignee}</div></div>
        </div>
        <div class="actions">
            <div class="chat" id="chatContainer">
                <img class="chat-icon" alt="">
                <div class="property-1default-div">0</div>
            </div>
            <div class="actionbuttons">
                <div class="chat">
                    <img class="chat-icon" alt="">
                    <div class="property-1default-div">Project Scope</div>
                </div>
            </div>
        </div>
    `;

  card.addEventListener("dragstart", handleDragStart);
  card.addEventListener("dragend", handleDragEnd);

  // Botão de editar
  const editBtn = document.createElement("button");
  editBtn.classList.add("edit-task");
  editBtn.innerHTML = `<i class="fa-solid fa-pen-to-square"></i>`;
  editBtn.style.position = "absolute";
  editBtn.style.bottom = "8px";
  editBtn.style.right = "10px";
  editBtn.style.background = "transparent";
  editBtn.style.border = "none";
  editBtn.style.cursor = "pointer";
  editBtn.style.color = "#0275d8";
  editBtn.style.fontSize = "16px";

  // Ao clicar em editar
  editBtn.addEventListener("click", (e) => {
    e.stopPropagation();
    editingCard = card;
    selectedColumn = card.closest(".kanban-column");

    const taskInfo = {
      title: card.querySelector(".registro-jwt").textContent,
      description: card.querySelector(".implementar-a-funo").textContent,
      priority: card.querySelector(".prioridade div").textContent,
      project: card.querySelector(".project-tag div").textContent,
      date: card.querySelector(".div").textContent,
      rawDate: (() => {
        const [d, m, y] = card.querySelector(".div").textContent.split("/");
        return `${y}-${m}-${d}`;
      })(),
      assignee: card.querySelector(".gabrielbegodex").textContent,
    };

    openModal(taskInfo);
  });

  // Botão de deletar
  const deleteBtn = document.createElement("button");
  deleteBtn.classList.add("delete-task");
  deleteBtn.innerHTML = `<i class="fa-solid fa-trash"></i>`;
  deleteBtn.style.position = "absolute";
  deleteBtn.style.bottom = "35px";
  deleteBtn.style.right = "10px";
  deleteBtn.style.background = "transparent";
  deleteBtn.style.border = "none";
  deleteBtn.style.cursor = "pointer";
  deleteBtn.style.color = "#d9534f";
  deleteBtn.style.fontSize = "16px";

  deleteBtn.addEventListener("click", (e) => {
    e.stopPropagation();
    if (confirm("Tem certeza que deseja excluir esta task?")) {
      card.remove();
    }
  });

  // Adiciona ambos os botões
  card.style.position = "relative";
  card.appendChild(deleteBtn);
  card.appendChild(editBtn);

  return card;
}

// Submeter formulário
taskForm.addEventListener("submit", (e) => {
  e.preventDefault();

  if (!selectedColumn) {
    alert("Erro: Nenhuma coluna selecionada");
    return;
  }

  const taskData = {
    title: document.getElementById("taskTitle").value,
    description: document.getElementById("taskDescription").value,
    priority: document.getElementById("taskPriority").value,
    project: document.getElementById("taskProject").value,
    date: formatDate(document.getElementById("taskDate").value),
    rawDate: document.getElementById("taskDate").value,
    assignee: document.getElementById("taskAssignee").value,
  };

  if (editingCard) {
    // Atualiza os dados do card existente
    editingCard.querySelector(".registro-jwt").textContent = taskData.title;
    editingCard.querySelector(".implementar-a-funo").textContent = taskData.description;
    editingCard.querySelector(".prioridade div").textContent = taskData.priority;
    editingCard.querySelector(".prioridade div").className = taskData.priority;
    editingCard.querySelector(".project-tag div").textContent = taskData.project;
    editingCard.querySelector(".div").textContent = taskData.date;
    editingCard.querySelector(".gabrielbegodex").textContent = taskData.assignee;

    console.log("Task editada com sucesso!", taskData);
  } else {
    // Cria nova task
    const newCard = createTaskCard(taskData);
    const cardsContainer = selectedColumn.querySelector(".kanban-cards");
    cardsContainer.appendChild(newCard);
    console.log("Task adicionada com sucesso!", taskData);
  }

  closeModal();
});


// Conecta os botões de editar e deletar dos cards já existentes no HTML
document.querySelectorAll('.kanban-card').forEach(card => {
  const editBtn = card.querySelector('.edit-task');
  const deleteBtn = card.querySelector('.delete-task');

  // 🟢 Editar task
  if (editBtn) {
    editBtn.addEventListener('click', (e) => {
      e.stopPropagation();
      editingCard = card;
      selectedColumn = card.closest(".kanban-column");

      const taskInfo = {
        title: card.querySelector(".registro-jwt").textContent,
        description: card.querySelector(".implementar-a-funo").textContent,
        priority: card.querySelector(".prioridade div").textContent,
        project: card.querySelector(".project-tag div").textContent,
        date: card.querySelector(".div").textContent,
        rawDate: (() => {
          const [d, m, y] = card.querySelector(".div").textContent.split("/");
          return `${y}-${m}-${d}`;
        })(),
        assignee: card.querySelector(".gabrielbegodex").textContent,
      };

      openModal(taskInfo); // abre o modal com os dados da task
    });
  }

  // 🔴 Deletar task
  if (deleteBtn) {
    deleteBtn.addEventListener('click', (e) => {
      e.stopPropagation();
      if (confirm("Tem certeza que deseja excluir esta task?")) {
        card.remove();
      }
    });
  }
});


