# Information System Architecture - WebServices<!-- omit in toc -->

<br/>

Authors: Théo MILLAIRE, Pierre FOURRE & Guillaume ELAMBERT

<br/>

## Table of contents<!-- omit in toc -->

- [1. Exercice 1](#1-exercice-1)
- [2. API Documentation](#2-api-documentation)
  - [2.1. List products](#21-list-products)
  - [2.2. Get a product](#22-get-a-product)
    - [Get product comments](#get-product-comments)
  - [2.3. Create a product](#23-create-a-product)
  - [2.4. Update a product](#24-update-a-product)
  - [2.5. Delete a product](#25-delete-a-product)

<br/><br/>

## 1. Exercice 1

<table>
    <thead>
        <th align="center">N°</th>
        <th align="center">URI</th>
        <th align="center">Input</th>
        <th align="center">Output</th>
    </thead>
    <tbody>
        <tr>
            <td rowspan="2" align="center">1</td>
            <td rowspan="2">GET /products</td>
            <td rowspan="2"></td>
            <td>application/json</td>
        </tr>
        <tr>
            <td>application/xml</td>
        </tr>
        <tr>
            <td rowspan="2" align="center">2</td>
            <td rowspan="2">GET /products/<strong><code>{product_id}</code></strong></td>
            <td rowspan="2"></td>
            <td>application/json</td>
        </tr>
        <tr><td>application/xml</td></tr>
        <tr>
            <td rowspan="2" align="center">3</td>
            <td rowspan="2">GET /products?category=<strong><code>{category_label}</code></strong></td>
            <td rowspan="2"></td>
            <td>application/json</td>
        </tr>
        <tr><td>application/xml</td></tr>
        <tr>
            <td rowspan="2" align="center">4</td>
            <td rowspan="2">GET /products?order=<strong><code>{asc | desc}</code></strong></td>
            <td rowspan="2"></td>
            <td>application/json</td>
        </tr>
        <tr><td>application/xml</td></tr>
        <tr>
            <td rowspan="2" align="center">5</td>
            <td rowspan="2">GET /products?currency=<strong><code>{currency_string}</code></strong></td>
            <td rowspan="2"></td>
            <td>application/json</td>
        </tr>
        <tr><td>application/xml</td></tr>
        <tr>
            <td align="center">6</td>
            <td>DELETE /products/<strong><code>{product_id}</code></strong></td>
            <td></td>
            <td></td>
        </tr>
        <tr>
            <td rowspan="2" align="center">7</td>
            <td rowspan="2">POST /products/</td>
            <td>application/json</td>
            <td rowspan="2"></td>
        </tr>
        <tr><td>application/xml</td></tr>
        <tr>
            <td rowspan="2" align="center">8</td>
            <td rowspan="2">PUT /products/<strong><code>{product_id}</code></strong></td>
            <td>application/json</td>
            <td rowspan="2"></td>
        </tr>
        <tr><td>application/xml</td></tr>
        <tr>
            <td rowspan="2" align="center">9</td>
            <td rowspan="2">PATCH /products/<strong><code>{product_id}</code></strong></td>
            <td>application/json</td>
            <td rowspan="2"></td>
        </tr>
        <tr><td>application/xml</td></tr>
        <tr>
            <td rowspan="2" align="center">10</td>
            <td rowspan="2">GET /products/<strong><code>{product_id}</code></strong>/comments</td>
            <td rowspan="2"></td>
            <td>application/json</td>
        </tr>
        <tr><td>application/xml</td></tr>
    </tbody>
</table>

<br/><br/>






## 2. API Documentation



<details>
<summary>

### 2.1. List products
</summary>

Endpoint : **`/products`** <br/>
Method : **`GET`**

<br/>


The following parameters can be used individually or combined.

| Query parameters                                                 |
| :--------------------------------------------------------------- |
| **`order`** *string* <br/> <p>Sorts products on price.</p> Can be one of : *`asc`* or *`desc`* <br/> Default : *`asc`*   |
| **`category`** *string* <br/> Filters products on their category label. |
| **`currency`** *string* <br/> <p>Returns the products with a new field : **`priceInCurrency`** containing the price of the product converted to the specified currency.</p> Can be any international currency abbreviations &emsp; (i.e. "usd", "gbp",...) <br/> Non case sensitive. |

</details>




<details>
<summary>

### 2.2. Get a product
</summary>

Endpoint : **`/products/{product_id}`** <br/>
Method : **`GET`**

<br/>


| Path parameters                                            |
| :--------------------------------------------------------- |
| **`product_id`** *int* <br/> The id of the wanted product. |

<br/>

The following parameters can be used individually or combined.

| Query parameters                                                 |
| :--------------------------------------------------------------- |
| **`order`** *string* <br/> <p>Sorts products on price.</p> Can be one of : *`asc`* or *`desc`* <br/> Default : *`asc`*   |
| **`category`** *string* <br/> Filters products on their category label. |
| **`currency`** *string* <br/> <p>Returns the products with a new field : **`priceInCurrency`** containing the price of the product converted to the specified currency.</p> Can be any international currency abbreviations &emsp; (i.e. "usd", "gbp",...) <br/> Non case sensitive. |


<br/>

<details>
<summary>

#### Get product comments
</summary>

Endpoint : **`/products/{product_id}/comments`** <br/>
Method : **`GET`**

<br/>


| Path parameters                                            |
| :--------------------------------------------------------- |
| **`product_id`** *int* <br/> The id of the wanted product. |

</details>

</details>




<details>
<summary>

### 2.3. Create a product
</summary>

Endpoint : **`/products`** <br/>
Method : **`POST`**

<br/>

| Body parameters                                                |
| :------------------------------------------------------------- |
| **`label`** *int* <br/> The label of the product.                    |
| **`category`** *string* <br/> The product category label.            |
| **`stock`** *int* <br/> The product stock quantity.                  |
| <span id="createProductPriceParam">**`priceInEuro`** or **`priceInCurrency`**</span> *double* <br/> The price of the product in : <ul><li>Euro if **`priceInEuro`** specified</li><li>[**`currency`**][createProductCurrencyParam] if both [**`currency`**][createProductCurrencyParam] and **`priceInCurrency`** parameters are specified. </ul></li> |

<br/>

| Query parameters                                                 |
| :--------------------------------------------------------------- |
| <span id="createProductCurrencyParam">**`currency`**</span> *string* <br/> <p>Update the price of the product using a conversion from the specified **`currency`** to Euro. See [**`priceInCurrency`**][createProductPriceParam].</p> Can be any international currency abbreviations &emsp; (i.e. "usd", "gbp",...) <br/> Non case sensitive. |

</details>




<details>
<summary>

### 2.4. Update a product
</summary>

<table>
    <tbody>
        <tr>
            <th>Endpoint</th>
            <td colspan="2" align="center"><strong><code>/products/{product_id}</code></strong></td>
        </tr>
        <tr>
            <th>Method</th>
            <td align="center"><strong><code>PUT</code></strong></td>
            <td align="center"><strong><code>PATCH</code></strong></td>
        </tr>
    </tbody>
</table>

<br/>


| Path parameters                                                |
| :------------------------------------------------------------- |
| **`product_id`** *int* <br/> The id of the you want to update. |


| Body parameters                                                |
| :------------------------------------------------------------- |
| **`label`** *int* <br/> The label of the product.                    |
| **`category`** *string* <br/> The product category label.            |
| **`stock`** *int* <br/> The product stock quantity.                  |
| <span id="updateProductPriceParam">**`priceInEuro`** or **`priceInCurrency`**</span> *double* <br/> The price of the product in : <ul><li>Euro if **`priceInEuro`** specified</li><li>[**`currency`**][updateProductCurrencyParam] if both [**`currency`**][updateProductCurrencyParam] and **`priceInCurrency`** parameters are specified. </ul></li> |

<br/>

| Query parameters                                                 |
| :--------------------------------------------------------------- |
| <span id="updateProductCurrencyParam">**`currency`**</span> *string* <br/> <p>Update the price of the product using a conversion from the specified **`currency`** to Euro. See [**`priceInCurrency`**][updateProductPriceParam].</p> Can be any international currency abbreviations &emsp; (i.e. "usd", "gbp",...) <br/> Non case sensitive. |

</details>




<details>
<summary>

### 2.5. Delete a product
</summary>

Endpoint : **`/products/{product_id}`** <br/>
Method : **`DELETE`**

<br/>

| Path parameters                                                      |
| :------------------------------------------------------------------- |
| **`product_id`** *int* <br/> The id of the you want to delete. |

</details>


[createProductCurrencyParam]: #createProductCurrencyParam
[createProductPriceParam]: #createProductPriceParam
[updateProductCurrencyParam]: #updateProductCurrencyParam
[updateProductPriceParam]: #updateProductPriceParam