import './Lamp.css';

const OFF = 'O';

export default function Lamp({ state, color }) {
  const lit = state !== OFF;
  return (
    <span
      data-testid="lamp"
      data-lit={lit}
      data-color={color}
      className={`lamp lamp--${color}${lit ? ' lamp--lit' : ''}`}
    />
  );
}
