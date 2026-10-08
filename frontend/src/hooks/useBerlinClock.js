import { useEffect, useState } from 'react';
import { fetchClock } from '../api/clockApi.js';

const REFRESH_INTERVAL_MS = 1000;

// loads the Berlin Clock from the backend
export default function useBerlinClock(fixedTime) {
  const [clock, setClock] = useState(null); // state for backend response
  const [error, setError] = useState(null); // state for backend exception

  // ideally I'd use tanstack (instead of useEffect) to avoid boirpolate and avoid race condition but for this kata useEffect should be enough
  useEffect(() => {
    let cancelled = false; // https://react.dev/reference/react/useEffect - check for race conditions

    const load = async () => {
      try {
        const result = await fetchClock(fixedTime);
        if (!cancelled) {
          setClock(result);
          setError(null);
        }
      } catch (e) {
        if (!cancelled) {
          setError(e.message);
        }
      }
    };

    load(); // run as soon as component mounts

    const timer =
      fixedTime === undefined // if the clock is live
        ? setInterval(load, REFRESH_INTERVAL_MS) // setInterval params: (callback, delay) - a browser timer calls load every 1s and timer holds its id.
        : null; // otherwise (time was chosen), doesn't reload and timer is null.

    return () => {
      cancelled = true;
      clearInterval(timer);
    };
  }, [fixedTime]); // runs every time fixedTime changes

  return { clock, error };
}
