import './Lamp.css';

const OFF = 'O';

export default function Lamp({ state, color }) {
  const lit = state !== OFF;
  return (
    <span
      data-testid="lamp" // needed for test
      data-lit={lit} // needed for test
      data-color={color} // needed for test
      className={`lamp lamp--${color}${lit ? ' lamp--lit' : ''}`}
    />
  );
}
