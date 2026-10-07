import Lamp from '../Lamp/Lamp.jsx';
import './LampRow.css';

/** One row of the clock; `lamps` is the backend's text for it, e.g. "YYRO". */
export default function LampRow({ label, variant, lamps, colorAt }) {
  // lamps prop, e.g. "RROO"
  // all other properties, e.g. { label: 'Five hours', variant: 'hours', colorAt: () => 'red' }
  return (
    <div
      role="group"
      aria-label={label}
      className={`clock-row clock-row--${variant}`}
    >
      {[...lamps].map((initialSingleColorLamp, index) => (
        <Lamp
          key={index}
          state={initialSingleColorLamp}
          color={colorAt(index)}
        />
      ))}
    </div>
  );
}
