export const BASE_URL = "http://127.0.0.1:8080/"

// AUTH
export const REGISTER_URL = BASE_URL + "auth/register"
export const LOGIN_URL = BASE_URL + "auth/login"

//ESTORE
export const ESTORE_URL = BASE_URL + "estore/"
export const GET_PRODUCT = ESTORE_URL + "get_product?productId="
export const GET_PRODUCTS = ESTORE_URL + "get_products"
export const ADD_PRODUCTS = ESTORE_URL + "add_products"
export const GET_POINTS = ESTORE_URL + "get_points"
export const CREATE_POINT = ESTORE_URL + "create_point"
export const CREATE_ORDER = ESTORE_URL + "create_order"
export const GET_ORDERS = ESTORE_URL + "get_orders"
