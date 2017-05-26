### Wyświetlanie listy wszystkich produktów

GET http://localhost:8080/shop-1.0-SNAPSHOT/shop/products

### Wyświetlenie produktu o podanym id

GET http://localhost:8080/shop-1.0-SNAPSHOT/shop/products/{id}

### Dodanie nowego produktu

POST http://localhost:8080/shop-1.0-SNAPSHOT/shop/products

```javascript
Header:
Content-Type: application/json

Body:
{
    "name": "nazwa3",
    "price": "4.60",
    "category" : "GRAPHIC_CARD"
}
```

### Zaktualizowanie informacji o produkcie

PUT http://localhost:8080/shop-1.0-SNAPSHOT/shop/products/{id}

```javascript
Body:
{
    "name": "nazwa3",
    "price": "3.00",
    "category" : "RAM"
}
```

### Wyszukiwanie produktów po: 

* cenie

GET http://localhost:8080/shop-1.0-SNAPSHOT/shop/products?priceFrom={priceFrom}&priceTo={priceTo}

* nazwie

GET http://localhost:8080/shop-1.0-SNAPSHOT/shop/products?name={name}

* kategori

GET http://localhost:8080/shop-1.0-SNAPSHOT/shop/products?category={category}

