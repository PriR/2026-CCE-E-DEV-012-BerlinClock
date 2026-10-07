import { afterEach, describe, expect, it, vi } from 'vitest';
import { fetchClock } from './clockApi.js';

const sample = {
  display: { seconds: 'O', fiveHours: 'RROO', oneHours: 'RRRO', fiveMinutes: 'YYROOOOOOOO', oneMinutes: 'YYOO' },
};

function mockFetch(response) {
  const fetchMock = vi.fn().mockResolvedValue(response);
  vi.stubGlobal('fetch', fetchMock);
  return fetchMock;
}

describe('fetchClock', () => {
  afterEach(() => vi.unstubAllGlobals());

  it('requests the current time when no time is given', async () => {
    const fetchMock = mockFetch({ ok: true, json: async () => sample });

    const result = await fetchClock();

    expect(fetchMock).toHaveBeenCalledWith('/api/berlin-clock');
    expect(result).toEqual(sample);
  });

  it('requests a specific time, URL-encoded', async () => {
    const fetchMock = mockFetch({ ok: true, json: async () => sample });

    await fetchClock('13:17:01');

    expect(fetchMock).toHaveBeenCalledWith('/api/berlin-clock?time=13%3A17%3A01');
  });

  it('throws the backend problem detail when the request is rejected', async () => {
    mockFetch({
      ok: false,
      status: 400,
      json: async () => ({ detail: 'hours must be between 0 and 24: 25' }),
    });

    await expect(fetchClock('25:00:00')).rejects.toThrow('hours must be between 0 and 24: 25');
  });

  it('throws a generic message when the error body is not readable', async () => {
    mockFetch({
      ok: false,
      status: 500,
      json: async () => {
        throw new Error('not json');
      },
    });

    await expect(fetchClock()).rejects.toThrow('Request failed with status 500');
  });
});
