import { useState } from 'react';
import './TimeForm.css';

/**
 * Lets the user pick a custom HH:mm:ss time, or go back to the live time.
 * It only reports what the user chose; what to do with it is up to the parent.
 */
export default function TimeForm({ isLive, onShow, onLive }) {
  const [input, setInput] = useState('');

  const submit = (event) => {
    event.preventDefault();
    onShow(input.trim());
  };

  return (
    <form className="controls" onSubmit={submit}>
      <label>
        Custom time (HH:mm:ss)
        <input
          value={input}
          onChange={(event) => setInput(event.target.value)}
          placeholder="23:59:59"
        />
      </label>
      <button type="submit">Show time</button>
      <button type="button" onClick={onLive} disabled={isLive}>
        Live
      </button>
    </form>
  );
}
