const fileInput = document.querySelector('#file');
const submitButton = document.querySelector('#submit');
const form = document.querySelector('#uploadForm')

fileInput.addEventListener('change', () => {
    submitButton.disabled = !fileInput.files.length;
});

form.addEventListener('reset', () => {
    submitButton.disabled = true;
});