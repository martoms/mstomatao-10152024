export interface Task {
  id: string;
  name: string;
  description: string;
  status: string
};

export interface State {
  showForm: boolean;
  addTask: boolean;
  activeItem: string;
  isPending: boolean;
  error: string | null;
};

export interface Response {
  status: string;
  message: string;
  data?: Task[]
}

export interface HeaderOptions {
  method: 'GET' | 'POST' | 'PATCH' | 'DELETE'
  headers: {
    'Content-Type': 'application/json'
  }
  body?: string
}

export type Pending = boolean | undefined;
export type StateError = string | undefined;
export type Status = 'TO DO' | 'IN PROGRESS' | 'DONE';

export type UpdateTasksFN = (newTasks: Task[]) => void;
export type UpdateStateFN = (pending: Pending, error: StateError) => void;
export type UpdateDraggedItemFN = (item: string) => void;
export type UpdateDropZoneFN = (zone: string) => void;
export type UpdateTaskListFN = () => void
