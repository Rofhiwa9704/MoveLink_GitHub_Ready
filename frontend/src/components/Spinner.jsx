export default function Spinner({ text = "Loading..." }) {
  return <div className="spinner-wrap"><span className="spinner" />{text}</div>;
}
