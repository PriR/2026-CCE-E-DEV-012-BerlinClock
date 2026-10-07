import { useEffect, useState } from 'react';
import { fetchClock } from '../api/clockApi.js';

const REFRESH_INTERVAL_MS = 1000;

/**
 * Loads the Berlin Clock from the backend.
 * With no `fixedTime` it follows the live time, refreshing every second;
 * with an HH:mm:ss `fixedTime` it asks once.
 */
export default function useBerlinClock(fixedTime) {
  const [clock, setClock] = useState(null);
  const [error, setError] = useState(null);

  useEffect(() => {
    let cancelled = false;

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

    load();
    const timer = fixedTime === undefined ? setInterval(load, REFRESH_INTERVAL_MS) : null;

    return () => {
      cancelled = true;
      clearInterval(timer);
    };
  }, [fixedTime]);

  return { clock, error };
}
