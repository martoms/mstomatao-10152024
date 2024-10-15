export type Status = 'TO DO' | 'IN PROGRESS' | 'DONE'

export interface Task {
  name: string;
  status: Status;
  description: string;
}