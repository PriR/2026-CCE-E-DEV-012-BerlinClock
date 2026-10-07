import Lamp from '../Lamp/Lamp.jsx';
import './LampRow.css';

/** One row of the clock; `lamps` is the backend's text for it, e.g. "YYRO". */
export default function LampRow({ label, variant, lamps, colorAt }) {
  return (
    <div role="group" aria-label={label} className={`clock-row clock-row--${variant}`}>
      {[...lamps].map((state, index) => (
        <Lamp key={index} state={state} color={colorAt(index)} />
      ))}
    </div>
  );
}
