class Task {
  constructor(title, description) {
    this.id = Task.generateId();
    this.title = title;
    this.description = description;
    this.status = 'todo'; // Initial status
  }

  static generateId() {
    return Math.random().toString(36).substring(2, 9);
  }
}

class Kanban {
  constructor() {
    this.columns = {
      todo: [],
      doing: [],
      done: []
    };
  }

  addTask(title, description) {
    const task = new Task(title, description);
    this.columns.todo.push(task);
    return task;
  }

  moveTask(taskId, newStatus) {
    const statuses = ['todo', 'doing', 'done'];
    if (!statuses.includes(newStatus)) {
      throw new Error(`Invalid status "${newStatus}".`);
    }

    for (const status of statuses) {
      const taskIndex = this.columns[status].findIndex(t => t.id === taskId);
      if (taskIndex !== -1) {
        const [task] = this.columns[status].splice(taskIndex, 1);
        task.status = newStatus;
        this.columns[newStatus].push(task);
        return task;
      }
    }

    throw new Error(`Task with ID "${taskId}" not found.`);
  }

  getTasks(status) {
    if (!this.columns[status]) {
      throw new Error(`Column "${status}" does not exist.`);
    }
    return this.columns[status];
  }

  removeTask(taskId) {
    for (const status in this.columns) {
      const taskIndex = this.columns[status].findIndex(t => t.id === taskId);
      if (taskIndex !== -1) {
        return this.columns[status].splice(taskIndex, 1)[0];
      }
    }
    throw new Error(`Task with ID "${taskId}" not found.`);
  }
}

// Example Usage:
const myKanban = new Kanban();

const task1 = myKanban.addTask("Build Landing Page", "Create HTML/CSS for the new site");
const task2 = myKanban.addTask("Write Unit Tests", "Coverage for the new features");

myKanban.moveTask(task1.id, 'doing');
myKanban.moveTask(task2.id, 'done');

console.log('To-Do:', myKanban.getTasks('todo'));
console.log('Doing:', myKanban.getTasks('doing'));
console.log('Done:', myKanban.getTasks('done'));
