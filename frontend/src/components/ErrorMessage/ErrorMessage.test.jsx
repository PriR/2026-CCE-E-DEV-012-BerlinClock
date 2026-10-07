import { render, screen } from '@testing-library/react';
import { describe, expect, it } from 'vitest';
import ErrorMessage from './ErrorMessage.jsx';

describe('ErrorMessage', () => {
  it('shows the message as an alert', () => {
    render(<ErrorMessage message="time must use the HH:mm:ss format" />);

    expect(screen.getByRole('alert')).toHaveTextContent('time must use the HH:mm:ss format');
  });

  it('renders nothing when there is no message', () => {
    const { container } = render(<ErrorMessage message={null} />);

    expect(container).toBeEmptyDOMElement();
  });
});
