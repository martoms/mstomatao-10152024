import type { Task } from "@/types/types";
import { defineStore } from "pinia";
import { ref } from 'vue';

const useTaskStore = defineStore('tasks', () => {
  const tasks = ref<Task[]>([]);

  return { tasks }
})

export default useTaskStore;