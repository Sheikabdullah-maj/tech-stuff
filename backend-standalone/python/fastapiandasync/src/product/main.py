from fastapi import FastAPI
from .schemas import Product
from .import models
from .database import SessionLocal, engine
from fastapi import Depends
from sqlalchemy.orm import Session

app = FastAPI()

models.Base.metadata.create_all(bind=engine)

def get_db():
    db = SessionLocal()
    try:
        yield db
    finally:
        db.close()

@app.post("/product")
def create_product(product: Product, db: Session = Depends(get_db)):
    new_product = models.Product(id=product.id, name=product.name, price=product.price)
    db.add(new_product)
    db.commit()
    db.refresh(new_product)
    return product

@app.get("/products")
def get_products(db: Session = Depends(get_db)):
    return db.query(models.Product).all()

@app.get("/product/{product_id}")
def get_product(product_id: int, db: Session = Depends(get_db)):
    return db.query(models.Product).filter(models.Product.id == product_id).first()

@app.delete("/product/{product_id}")
def delete_product(product_id: int, db: Session = Depends(get_db)):
    product = db.query(models.Product).filter(models.Product.id == product_id).first()
    if product:
        db.delete(product)
        db.commit()
        return {"message": "Product deleted"}
    return {"message": "Product not found"}

@app.put("/product/{product_id}")
def update_product(product_id: int, product: Product, db: Session = Depends(get_db)):
    existing_product = db.query(models.Product).filter(models.Product.id == product_id).first()
    if existing_product:
        existing_product.name = product.name
        existing_product.price = product.price
        db.commit()
        db.refresh(existing_product)
        return existing_product
    return {"message": "Product not found"}
