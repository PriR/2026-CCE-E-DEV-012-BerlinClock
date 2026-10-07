import './ErrorMessage.css';

/** Shows an error as an alert; renders nothing when there is no message. */
export default function ErrorMessage({ message }) {
  if (!message) {
    return null;
  }
  return (
    <p role="alert" className="error">
      {message}
    </p>
  );
}
