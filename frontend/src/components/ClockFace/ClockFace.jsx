import { CLOCK_ROWS } from '../../config/clockRows.js';
import LampRow from '../LampRow/LampRow.jsx';
import './ClockFace.css';

export default function ClockFace({ display }) {
  return (
    <div className="clock-face">
      {CLOCK_ROWS.map(({ key, ...row }) => (
        <LampRow key={key} lamps={display[key]} {...row} />
      ))}
    </div>
  );
}
