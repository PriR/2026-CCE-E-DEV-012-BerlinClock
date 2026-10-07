/**
 * Fetches the Berlin Clock lamps from the backend.
 * @param {string} [time] optional HH:mm:ss; the backend uses its current time when omitted
 */
export async function fetchClock(time) {
  const url = time ? `/api/berlin-clock?time=${encodeURIComponent(time)}` : '/api/berlin-clock';
  const response = await fetch(url);

  if (!response.ok) {
    let message = `Request failed with status ${response.status}`;
    try {
      const problem = await response.json();
      if (problem && problem.detail) {
        message = problem.detail;
      }
    } catch {
      // body was not JSON: keep the generic message
    }
    throw new Error(message);
  }

  return response.json();
}
