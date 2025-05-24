import streamlit as st
import numpy as np
import tensorflow as tf
from sklearn.preprocessing import StandardScaler, OneHotEncoder, LabelEncoder
import pandas as pd
import pickle

model = tf.keras.models.load_model('model.h5')

with open('onehot_encoder_geo.pkl','rb') as file:
    onehot_encoder_geo = pickle.load(file)

with open('label_encoder_gender.pkl','rb') as file:
    label_encoder_gender = pickle.load(file)

with open('scaler.pkl','rb') as file:
    scaler = pickle.load(file)

## streamlit app title
st.title('Customer Churn Prediction')

##user input
geography= st.selectbox('Geography', onehot_encoder_geo.categories_[0])
gender= st.selectbox('Gender', label_encoder_gender.classes_)
age=st.slider('Age', 18, 75)
balance= st.number_input('Balance')
credit_score=st.number_input('Credit Score')
estimated_salary=st.number_input('Estimated Salary')
tenure=st.slider('Tenure', 0, 10)
number_of_products=st.slider('Number of products', 1, 4)
has_cr_card=st.selectbox('Has Credit Card',[0,1])
is_active_member=st.selectbox('Is Active Member', [0,1])

input_data = pd.DataFrame({
    'CreditScore' : [credit_score],
    'Gender' : [label_encoder_gender.transform([gender])[0]],
    'Age' : [age],
    'Tenure' :[tenure],
    'Balance' : [balance],
    'NumOfProducts' : [number_of_products],
    'HasCrCard' : [has_cr_card],
    'IsActiveMember' : [is_active_member],
    'EstimatedSalary' : [estimated_salary]

})

geo_encoded = onehot_encoder_geo.transform([[geography]]).toarray()
geo_encoded_df = pd.DataFrame(geo_encoded, columns=onehot_encoder_geo.get_feature_names_out(['Geography']))

input_data = pd.concat([input_data.reset_index(drop=True), geo_encoded_df], axis=1)
input_scaled = scaler.transform(input_data)

prediction = model.predict(input_scaled)
prediction_probability = prediction[0][0]


if prediction_probability > 0.5:
    st.write('The customer likely to churn')
else:
    st.write('The customer not likely to churn')

