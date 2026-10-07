import { act, fireEvent, render, screen } from '@testing-library/react';
import { afterEach, beforeEach, describe, expect, it, vi } from 'vitest';
import App from './App.jsx';
import { fetchClock } from './api/clockApi.js';

vi.mock('./api/clockApi.js');

const liveResponse = {
  seconds: 'O',
  fiveHours: 'RROO',
  oneHours: 'RRRO',
  fiveMinutes: 'YYROOOOOOOO',
  oneMinutes: 'YYOO',
};

const endOfDayResponse = {
  seconds: 'Y',
  fiveHours: 'RRRR',
  oneHours: 'RRRR',
  fiveMinutes: 'OOOOOOOOOOO',
  oneMinutes: 'OOOO',
};

// let pending promises (the mocked fetch) settle inside act()
const flush = () => act(async () => {});
const tick = (ms) => act(async () => vi.advanceTimersByTime(ms));

// the first lamp is the seconds lamp: lit in the end-of-day response, off in the live one
const secondsLamp = () => screen.getAllByTestId('lamp')[0];

describe('App', () => {
  beforeEach(() => {
    vi.useFakeTimers();
    fetchClock.mockReset();
    fetchClock.mockResolvedValue(liveResponse);
  });

  afterEach(() => {
    vi.useRealTimers();
  });

  it('shows the 24 lamps returned by the backend', async () => {
    render(<App />);
    await flush();

    expect(screen.getAllByTestId('lamp')).toHaveLength(24);
    expect(secondsLamp()).toHaveAttribute('data-lit', 'false');
  });

  it('does not show a digital time next to the lamps', async () => {
    const { container } = render(<App />);
    await flush();

    expect(container.querySelector('time')).toBeNull();
  });

  it('asks the backend for the current time every second', async () => {
    render(<App />);
    await flush();
    expect(fetchClock).toHaveBeenCalledTimes(1);
    expect(fetchClock).toHaveBeenLastCalledWith(undefined);

    await tick(1000);
    expect(fetchClock).toHaveBeenCalledTimes(2);

    await tick(2000);
    expect(fetchClock).toHaveBeenCalledTimes(4);
  });

  it('shows a custom time and stops refreshing until "Live" is pressed', async () => {
    render(<App />);
    await flush();
    fetchClock.mockResolvedValue(endOfDayResponse);

    fireEvent.change(screen.getByLabelText(/custom time/i), {
      target: { value: '24:00:00' },
    });
    fireEvent.click(screen.getByRole('button', { name: /show time/i }));
    await flush();

    expect(fetchClock).toHaveBeenLastCalledWith('24:00:00');
    expect(secondsLamp()).toHaveAttribute('data-lit', 'true');

    const callsAfterCustomTime = fetchClock.mock.calls.length;
    await tick(3000);
    expect(fetchClock).toHaveBeenCalledTimes(callsAfterCustomTime);

    fetchClock.mockResolvedValue(liveResponse);
    fireEvent.click(screen.getByRole('button', { name: /live/i }));
    await flush();
    expect(fetchClock).toHaveBeenLastCalledWith(undefined);
    expect(secondsLamp()).toHaveAttribute('data-lit', 'false');

    await tick(1000);
    expect(fetchClock.mock.calls.length).toBeGreaterThan(
      callsAfterCustomTime + 1,
    );
  });

  it('shows the backend error and keeps the previous lamps when the time is invalid', async () => {
    render(<App />);
    await flush();
    fetchClock.mockRejectedValue(
      new Error('hours must be between 0 and 24: 25'),
    );

    fireEvent.change(screen.getByLabelText(/custom time/i), {
      target: { value: '25:00:00' },
    });
    fireEvent.click(screen.getByRole('button', { name: /show time/i }));
    await flush();

    expect(screen.getByRole('alert')).toHaveTextContent(
      'hours must be between 0 and 24: 25',
    );
    expect(screen.getAllByTestId('lamp')).toHaveLength(24);
  });
});
