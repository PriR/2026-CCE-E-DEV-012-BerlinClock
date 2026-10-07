import { act, renderHook } from '@testing-library/react';
import { afterEach, beforeEach, describe, expect, it, vi } from 'vitest';
import { fetchClock } from '../api/clockApi.js';
import useBerlinClock from './useBerlinClock.js';

vi.mock('../api/clockApi.js');

const reading = { display: { seconds: 'O' } };

const flush = () => act(async () => {});
const tick = (ms) => act(async () => vi.advanceTimersByTime(ms));

describe('useBerlinClock', () => {
  beforeEach(() => {
    vi.useFakeTimers();
    fetchClock.mockReset();
    fetchClock.mockResolvedValue(reading);
  });

  afterEach(() => {
    vi.useRealTimers();
  });

  it('loads the clock from the backend', async () => {
    const { result } = renderHook(() => useBerlinClock(undefined));
    expect(result.current.clock).toBeNull();

    await flush();

    expect(result.current.clock).toEqual(reading);
    expect(result.current.error).toBeNull();
  });

  it('keeps refreshing every second while following the live time', async () => {
    renderHook(() => useBerlinClock(undefined));
    await flush();

    await tick(3000);

    expect(fetchClock).toHaveBeenCalledTimes(4);
    expect(fetchClock).toHaveBeenLastCalledWith(undefined);
  });

  it('asks once for a fixed time and does not refresh', async () => {
    renderHook(() => useBerlinClock('24:00:00'));
    await flush();

    await tick(3000);

    expect(fetchClock).toHaveBeenCalledTimes(1);
    expect(fetchClock).toHaveBeenCalledWith('24:00:00');
  });

  it('exposes the error message when the backend rejects the request', async () => {
    fetchClock.mockRejectedValue(new Error('time must use the HH:mm:ss format'));

    const { result } = renderHook(() => useBerlinClock('noon'));
    await flush();

    expect(result.current.error).toBe('time must use the HH:mm:ss format');
  });

  it('clears the error once a later request succeeds', async () => {
    fetchClock.mockRejectedValueOnce(new Error('boom'));

    const { result } = renderHook(() => useBerlinClock(undefined));
    await flush();
    expect(result.current.error).toBe('boom');

    await tick(1000);

    expect(result.current.error).toBeNull();
    expect(result.current.clock).toEqual(reading);
  });

  it('stops refreshing when unmounted', async () => {
    const { unmount } = renderHook(() => useBerlinClock(undefined));
    await flush();
    unmount();

    await tick(3000);

    expect(fetchClock).toHaveBeenCalledTimes(1);
  });
});
