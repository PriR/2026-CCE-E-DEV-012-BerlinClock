import { render, screen, within } from '@testing-library/react';
import ClockFace from './ClockFace.jsx';

const endOfDay = {
  seconds: 'O',
  fiveHours: 'RRRR',
  oneHours: 'RRRO',
  fiveMinutes: 'YYRYYRYYRYY',
  oneMinutes: 'YYYY',
};

const midnight = {
  seconds: 'Y',
  fiveHours: 'OOOO',
  oneHours: 'OOOO',
  fiveMinutes: 'OOOOOOOOOOO',
  oneMinutes: 'OOOO',
};

const lampsOf = (rowName) =>
  within(screen.getByRole('group', { name: rowName })).getAllByTestId('lamp');

describe('ClockFace', () => {
  it('renders the five rows with 1, 4, 4, 11 and 4 lamps', () => {
    render(<ClockFace display={midnight} />);

    expect(lampsOf('Seconds')).toHaveLength(1);
    expect(lampsOf('Five hours')).toHaveLength(4);
    expect(lampsOf('One hours')).toHaveLength(4);
    expect(lampsOf('Five minutes')).toHaveLength(11);
    expect(lampsOf('One minutes')).toHaveLength(4);
  });

  it('lights the seconds lamp when it is on and leaves it dark when it is off', () => {
    const { rerender } = render(<ClockFace display={midnight} />);
    expect(lampsOf('Seconds')[0]).toHaveAttribute('data-lit', 'true');

    rerender(<ClockFace display={endOfDay} />);
    expect(lampsOf('Seconds')[0]).toHaveAttribute('data-lit', 'false');
  });

  it('lights lamps according to the display strings', () => {
    render(<ClockFace display={endOfDay} />);

    expect(lampsOf('Five hours').map((l) => l.dataset.lit)).toEqual(['true', 'true', 'true', 'true']);
    expect(lampsOf('One hours').map((l) => l.dataset.lit)).toEqual(['true', 'true', 'true', 'false']);
    expect(lampsOf('One minutes').map((l) => l.dataset.lit)).toEqual(['true', 'true', 'true', 'true']);
  });

  it('colours hour lamps red and the seconds and one-minute lamps yellow', () => {
    render(<ClockFace display={endOfDay} />);

    lampsOf('Five hours').forEach((l) => expect(l).toHaveAttribute('data-color', 'red'));
    lampsOf('One hours').forEach((l) => expect(l).toHaveAttribute('data-color', 'red'));
    expect(lampsOf('Seconds')[0]).toHaveAttribute('data-color', 'yellow');
    lampsOf('One minutes').forEach((l) => expect(l).toHaveAttribute('data-color', 'yellow'));
  });

  it('makes every third five-minute lamp red, lit or not', () => {
    render(<ClockFace display={midnight} />);

    const colours = lampsOf('Five minutes').map((l) => l.dataset.color);
    expect(colours).toEqual([
      'yellow', 'yellow', 'red',
      'yellow', 'yellow', 'red',
      'yellow', 'yellow', 'red',
      'yellow', 'yellow',
    ]);
  });
});
