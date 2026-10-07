import { useState } from 'react';
import ClockFace from './components/ClockFace/ClockFace.jsx';
import ErrorMessage from './components/ErrorMessage/ErrorMessage.jsx';
import TimeForm from './components/TimeForm/TimeForm.jsx';
import useBerlinClock from './hooks/useBerlinClock.js';
import './App.css';

export default function App() {
  // undefined = follow the backend's current time; OR
  // a string = a fixed HH:mm:ss chosen by the user
  const [fixedTime, setFixedTime] = useState(undefined);
  const { clock, error } = useBerlinClock(fixedTime);

  return (
    <main className="app">
      <h1>Berlin Clock</h1>

      {clock ? (
        <ClockFace clock={clock} />
      ) : (
        !error && <p>Loading…</p> // When loading for the first time
      )}

      <ErrorMessage message={error} />

      <TimeForm
        isLive={fixedTime === undefined}
        onShow={setFixedTime}
        onLive={() => setFixedTime(undefined)}
      />
    </main>
  );
}
