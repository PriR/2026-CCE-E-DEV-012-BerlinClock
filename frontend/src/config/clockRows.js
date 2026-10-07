// What the backend sends for each row, and how its lamps are coloured and laid out.
// Adding or changing a row means editing this list only.
export const CLOCK_ROWS = [
  { key: 'seconds', label: 'Seconds', variant: 'seconds', colorAt: () => 'yellow' },
  { key: 'fiveHours', label: 'Five hours', variant: 'hours', colorAt: () => 'red' },
  { key: 'oneHours', label: 'One hours', variant: 'hours', colorAt: () => 'red' },
  {
    key: 'fiveMinutes',
    label: 'Five minutes',
    variant: 'five-minutes',
    // every third lamp marks a quarter of an hour
    colorAt: (index) => ((index + 1) % 3 === 0 ? 'red' : 'yellow'), // follows same rule as backend
  },
  { key: 'oneMinutes', label: 'One minutes', variant: 'minutes', colorAt: () => 'yellow' },
];
