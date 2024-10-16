//const apiUrl = 'https://labserverapacheback.duckdns.org/api';

const apiUrl = 'http://localhost:8080/api';

// Redireccionar a la página de login si no hay sesión iniciada
window.addEventListener('load', function () {
    const currentPage = window.location.pathname;

    if (!localStorage.getItem('loggedIn') && 
        !currentPage.includes('login.html') && 
        !currentPage.includes('register.html')) {
        window.location.href = 'login.html';
    }
});

// **Manejo del formulario de inicio de sesión**
document.getElementById('loginForm')?.addEventListener('submit', function (event) {
    event.preventDefault();

    const username = document.getElementById('loginUsername').value;
    const password = document.getElementById('loginPassword').value;

    fetch(`${apiUrl}/users/authenticate`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ username, password }),
        credentials: 'include' // Enviar cookies para mantener la sesión
    })
    .then(response => {
        if (response.ok) {
            localStorage.setItem('loggedIn', 'true'); 
            alert('Inicio de sesión exitoso');
            window.location.href = 'properties.html';
        } else {
            alert('Credenciales inválidas');
        }
    })
    .catch(error => console.error('Error:', error));
});

// **Manejo del formulario de registro**
document.getElementById('registerForm')?.addEventListener('submit', function (event) {
    event.preventDefault();

    const username = document.getElementById('registerUsername').value;
    const password = document.getElementById('registerPassword').value;

    fetch(`${apiUrl}/users/register`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ username, password }),
        credentials: 'include'
    })
    .then(response => {
        if (response.ok) {
            alert('Usuario registrado exitosamente');
            window.location.href = 'login.html';
        } else {
            alert('Error al registrar el usuario');
        }
    })
    .catch(error => console.error('Error:', error));
});

// **Cerrar sesión**
document.getElementById('logoutButton')?.addEventListener('click', function () {
    localStorage.removeItem('loggedIn'); // Eliminar sesión
    alert('Sesión cerrada');
    window.location.href = 'login.html';
});

// **Agregar una nueva propiedad**
document.getElementById('propertyForm')?.addEventListener('submit', function (event) {
    event.preventDefault();

    const property = {
        address: document.getElementById('address').value,
        price: parseFloat(document.getElementById('price').value),
        size: parseFloat(document.getElementById('size').value),
        description: document.getElementById('description').value
    };

    fetch(`${apiUrl}/properties`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(property),
        credentials: 'include'
    })
    .then(response => response.json())
    .then(data => {
        console.log('Propiedad creada:', data);
        loadProperties(); 
        document.getElementById('propertyForm').reset();
    })
    .catch(error => console.error('Error:', error));
});

// **Cargar propiedades**
function loadProperties() {
    fetch(`${apiUrl}/properties`, {
        method: 'GET',
        credentials: 'include'
    })
    .then(response => response.json())
    .then(data => {
        const propertyList = document.getElementById('propertyList');
        propertyList.innerHTML = '';

        data.forEach(property => {
            const li = document.createElement('li');
            li.textContent = `${property.address} - $${property.price} - ${property.size}m² - ${property.description}`;

            const updateButton = document.createElement('button');
            updateButton.textContent = 'Actualizar';
            updateButton.onclick = () => updateProperty(property.id);

            const deleteButton = document.createElement('button');
            deleteButton.textContent = 'Eliminar';
            deleteButton.onclick = () => deleteProperty(property.id);

            li.appendChild(updateButton);
            li.appendChild(deleteButton);
            propertyList.appendChild(li);
        });
    })
    .catch(error => console.error('Error:', error));
}

// **Eliminar una propiedad**
function deleteProperty(id) {
    fetch(`${apiUrl}/properties/${id}`, {
        method: 'DELETE',
        credentials: 'include'
    })
    .then(() => loadProperties())
    .catch(error => console.error('Error:', error));
}

// **Actualizar una propiedad**
function updateProperty(id) {
    const property = {
        address: prompt('Nueva dirección:'),
        price: parseFloat(prompt('Nuevo precio:')),
        size: parseFloat(prompt('Nuevo tamaño (m²):')),
        description: prompt('Nueva descripción:')
    };

    fetch(`${apiUrl}/properties/${id}`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(property),
        credentials: 'include'
    })
    .then(response => response.json())
    .then(data => {
        console.log('Propiedad actualizada:', data);
        loadProperties();
    })
    .catch(error => console.error('Error:', error));
}

// **Cargar propiedades al entrar en properties.html**
if (window.location.pathname.includes('properties.html')) {
    loadProperties();
}
