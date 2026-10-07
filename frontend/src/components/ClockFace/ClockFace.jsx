import './ClockFace.css';

// what the backend sends for each row, and how its lamps are coloured and laid out
const ROWS = [
  { key: 'seconds', label: 'Seconds', variant: 'seconds', colorAt: () => 'yellow' },
  { key: 'fiveHours', label: 'Five hours', variant: 'hours', colorAt: () => 'red' },
  { key: 'oneHours', label: 'One hours', variant: 'hours', colorAt: () => 'red' },
  {
    key: 'fiveMinutes',
    label: 'Five minutes',
    variant: 'five-minutes',
    // every third lamp marks a quarter of an hour
    colorAt: (index) => ((index + 1) % 3 === 0 ? 'red' : 'yellow'),
  },
  { key: 'oneMinutes', label: 'One minutes', variant: 'minutes', colorAt: () => 'yellow' },
];

export default function ClockFace({ display }) {
  return (
    <div className="clock-face">
      {ROWS.map(({ key, label, variant, colorAt }) => (
        <div key={key} role="group" aria-label={label} className={`clock-row clock-row--${variant}`}>
          {[...display[key]].map((state, index) => {
            const color = colorAt(index);
            const lit = state !== 'O';
            return (
              <span
                key={index}
                data-testid="lamp"
                data-lit={lit}
                data-color={color}
                className={`lamp lamp--${color}${lit ? ' lamp--lit' : ''}`}
              />
            );
          })}
        </div>
      ))}
    </div>
  );
}
