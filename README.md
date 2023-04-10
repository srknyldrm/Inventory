# Inventory

Working Platforms
-----------------
- Java 8 must be java 11 or hihger
- Spring Boot 2.3 +
- Mysql
- This project runs on port 8080 on tomcat


Usage Platforms
-----------------
  Product crud Operations
  -----------------------
  - Get http://localhost:8080/products - lists all products.
    ![image](https://user-images.githubusercontent.com/6370588/230985158-005a8830-fcf8-4f2c-ae5c-3600c3b79651.png)
  
  - Post http://localhost:8080/products - Adds product.
   ![image](https://user-images.githubusercontent.com/6370588/230985780-8dc3da37-58ea-4239-8755-94d3393ef2c0.png)
  
  - Put http://localhost:8080/products/{product_id} - Updates with the id of the product
    ![image](https://user-images.githubusercontent.com/6370588/230986264-83a94967-fe54-4e1c-980a-ff1acee6bb8c.png)

  - Delete http://localhost:8080/products/{product_id}
  
  Inventory crud Operations
  -------------------------
  - Get http://localhost:8080/api/inventory - lists al inventory
    ![image](https://user-images.githubusercontent.com/6370588/230986999-fe61f3fc-f987-467a-a1f9-4722447b9375.png)
  
  - Post http://localhost:8080/api/inventory/ -It checks that the product is registered and provides control so that a product is added to each warehouse. If necessary conditions are met, it is recorded in the inventory.
    ![image](https://user-images.githubusercontent.com/6370588/230987202-058c1da6-d99e-48cb-bf48-d239e5ac5a65.png)
    
  - Put http://localhost:8080/api/inventory/{id} - Updates with the id of the inventory
    ![image](https://user-images.githubusercontent.com/6370588/230988066-d68be0bb-872c-4867-8d4d-1ffd5037011f.png)

  - Remove http://localhost:8080/api/inventory/remove?removeQuantity={value} - Records are dropped from the relevant repository as much as the removeQuantity value. It checks the equality of warehouse and product information. Prints log if below critical level
    ![image](https://user-images.githubusercontent.com/6370588/230988930-d562cdcc-59dc-469a-82fc-f7d98fb567d7.png)
  
  - Delete http://localhost:8080/api/inventory/{inventory_id}
    ![image](https://user-images.githubusercontent.com/6370588/230989224-932484d2-5607-4aa6-a301-6b9616503e9d.png)

  Filter Operations
  -----------------
   - Filters by catalog http://localhost:8080/products/category/{category_id}
    ![image](https://user-images.githubusercontent.com/6370588/230989775-3ec39238-f27c-4a9e-aae8-1be99b783d56.png)
  
  - Filters by cities http://localhost:8080/api/inventory/city/{city_Name}
    ![image](https://user-images.githubusercontent.com/6370588/230990084-3210bb96-606a-49e7-9ec1-10c00c45a0a7.png)

  - Filters by warehouse http://localhost:8080/api/inventory/warehouse/{warehouse_işd}
    ![image](https://user-images.githubusercontent.com/6370588/230990498-7a83b11f-2dfa-4ece-a65a-c7ed3c0f7dde.png)
    
  - Filters by region
    ![image](https://user-images.githubusercontent.com/6370588/230990731-3a67b91a-bb60-4bfc-8118-ec6d71ec33c5.png)


