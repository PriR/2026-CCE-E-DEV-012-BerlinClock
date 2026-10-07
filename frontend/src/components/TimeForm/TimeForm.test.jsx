import { fireEvent, render, screen } from '@testing-library/react';
import { describe, expect, it, vi } from 'vitest';
import TimeForm from './TimeForm.jsx';

const type = (value) =>
  fireEvent.change(screen.getByLabelText(/custom time/i), { target: { value } });

describe('TimeForm', () => {
  it('reports the entered time without surrounding spaces when submitted', () => {
    const onShow = vi.fn();
    render(<TimeForm isLive onShow={onShow} onLive={() => {}} />);

    type('  23:59:59 ');
    fireEvent.click(screen.getByRole('button', { name: /show time/i }));

    expect(onShow).toHaveBeenCalledWith('23:59:59');
  });

  it('goes back to live time when "Live" is pressed', () => {
    const onLive = vi.fn();
    render(<TimeForm isLive={false} onShow={() => {}} onLive={onLive} />);

    fireEvent.click(screen.getByRole('button', { name: /live/i }));

    expect(onLive).toHaveBeenCalledTimes(1);
  });

  it('disables "Live" while the clock is already live', () => {
    render(<TimeForm isLive onShow={() => {}} onLive={() => {}} />);

    expect(screen.getByRole('button', { name: /live/i })).toBeDisabled();
  });
});
