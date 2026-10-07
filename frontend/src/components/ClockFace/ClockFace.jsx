import { CLOCK_ROWS } from '../../config/clockRows.js';
import LampRow from './LampRow.jsx';
import './ClockFace.css';

export default function ClockFace({ clock }) {
  return (
    <div className="clock-face">
      {CLOCK_ROWS.map(({ key, ...row }) => (
        <LampRow key={key} lamps={clock[key]} {...row} />
      ))}
    </div>
  );
}
