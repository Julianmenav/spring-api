/** @type {import('tailwindcss').Config} */
module.exports = {
    content: ["./src/main/resources/templates/**/*.{html,js}"],
    theme: {
      extend: {
          fontFamily: {
            'monse': ['Montserrat', 'sans-serif']
          },
      },
    },
    plugins: [],
  }