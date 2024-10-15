import type { UpdateTasksFN, UpdateStateFN, Response, HeaderOptions } from '../types/types.js';

const useFetch = async (
  api: string,
  headerOptions: HeaderOptions | undefined,
  updateTasksFN: UpdateTasksFN,
  updateStateFN: UpdateStateFN
) => {
  try {
    updateStateFN(true, undefined)
    const res = headerOptions
    ? await fetch(api, headerOptions)
    : await fetch(api)
    const json = await res.json() as Response;

    if (json.status === 'error') {
      throw new Error(json.message)
    }

    if (json.status === 'ok' && json.data) {
      updateTasksFN(json.data)
    }

  } catch(error: unknown) {
    if (typeof error === 'object' && error instanceof Error) {
      updateStateFN(undefined, error.message)
    }

  } finally {
    updateStateFN(false, undefined)
  }
}

export default useFetch;