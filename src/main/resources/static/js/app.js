function showAuthForm(type) {
    const loginForm = document.getElementById('login-form');
    const registerForm = document.getElementById('register-form');
    const authFormContainer = document.getElementById('auth-form-container');

    if (type === 'login') {
        loginForm.style.display = 'block';
        registerForm.style.display = 'none';
    } else if (type === 'register') {
        registerForm.style.display = 'block';
        loginForm.style.display = 'none';
    }
    authFormContainer.style.display = 'block';
}


// получить скины героев из баззы данных по имени героя
document.querySelectorAll('.list-group-item').forEach(item => {
    item.addEventListener('click', function () {
        const heroNameSpan = this.querySelector('.hero-name');
        const heroName = heroNameSpan.textContent.trim();

        fetch(`/api/skins/${heroName}`)
            .then(response => response.json())
            .then(data => {
                renderSkins(data);
                const cartSection = document.getElementById('cart-section');
                if (cartSection.style.display !== 'none') {
                    cartSection.style.display = 'none';
                }
            })
            .catch(error => console.error('Ошибка:', error));
    });
});
// вывести скины на сайт
const renderSkins = skins => {
    document.getElementById('skins-section').style.display = 'block';
    let container = document.getElementById('skins-container');
    container.innerHTML = '';

    if (!skins || skins.length === 0) {
        container.textContent = 'Нет доступных скинов.';
        return;
    }

    skins.forEach(skin => {
        let card = `
            <div class="card">
                <img src="/images/${skin.name.toLowerCase()}_${skin.heroName.toLowerCase()}.png" alt="${skin.name}">
                <p><strong>Название:</strong> ${skin.name}</p>
                <p><strong>Цена:</strong> ${skin.price}$</p>
                <button onclick="addToBasket(${skin.id})">Добавить в корзину</button>
            </div>
        `;
        container.insertAdjacentHTML('beforeend', card);
    });
};

//добавить скин в корзину
function addToBasket(skinId) {
    fetch(`/api/basket/addToBasket/${skinId}`)
        .then(response => response.text())
        .then(result => {
            if (result === "ok") {
                console.log("скин добавлен");
            } else {
                console.log("скин не добавлен");
            }
        });
}

// получить скины с корзины текушего пользователя
function loadCart() {
    fetch('/api/basket')
        .then(response => {
            console.log('Получен ответ:', response.status);
            return response.json()
        })
        .then(renderCart)
        .catch(error => {
            console.error('Ошибка загрузки корзины:', error);
        });
}

// показваем содержимое корзины
function renderCart(items) {
    let cartSection = document.getElementById('cart-section');
    let cartItemsContainer = document.getElementById('cart-items');
    cartItemsContainer.innerHTML = '';

    if (items && items.length > 0) {
        cartSection.style.display = 'block';
        items.forEach((item) => {
            let card = `
                <div class="card">
                    <img src="/images/${item.name.toLowerCase()}_${item.heroName.toLowerCase()}.png" alt="${item.name}">
                    <p><strong>Название:</strong> ${item.name}</p>
                    <p><strong>Цена:</strong> ${item.price}$</p>
                    <button onclick="removeFromCart(${item.id})">Удалить из корзины</button>
                </div>
            `;
            cartItemsContainer.insertAdjacentHTML('beforeend', card);
        });
    } else {
        cartItemsContainer.textContent = 'Корзина пуста!';
    }
}

// Открытие корзины по кнопке
document.getElementById('open-basket-btn').addEventListener('click', () => {
    const cartSection = document.getElementById('cart-section');
    const skinsSection = document.getElementById('skins-section');
    if (cartSection.style.display === 'none') {
        if (skinsSection.style.display !== 'none') {
            skinsSection.style.display = 'none';
        }
        loadCart();
        setTimeout(() => {
            cartSection.style.display = 'block';
        }, 10);
    } else {
        cartSection.style.display = 'none';
    }
});
