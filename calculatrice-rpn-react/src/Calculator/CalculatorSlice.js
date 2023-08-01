import { createSlice } from "@reduxjs/toolkit";
import axios from 'axios';
import Ressources from './Ressources';

const axiosWithHeaders = axios.create();
axiosWithHeaders.defaults.withCredentials = true; //axiosCsys.defaults.timeout = 10000;

axiosWithHeaders.defaults.responseType = "json";
axiosWithHeaders.defaults.headers.common['Authorization'] = localStorage.getItem("x-auth-token");
axiosWithHeaders.defaults.headers.common['x-auth-token'] = localStorage.getItem("x-auth-token");
axiosWithHeaders.defaults.headers.post['Content-Type'] = 'application/json;charset=UTF-8';
axiosWithHeaders.defaults.headers.put['Content-Type'] = 'application/json;charset=UTF-8';
axiosWithHeaders.defaults.headers.delete['Content-Type'] = 'application/json;charset=UTF-8';

export const getAllOperator = () => {
    return dispatch => {
        axios.get(`${Ressources.CoreUrl}/${Ressources.Calculatrice.api}/${Ressources.Calculatrice.operator}`)
            .then(res => {
                dispatch(CalculatorSlice.actions.getAllOperator(res.data));
            })
    }
};


export const getAllStack = () => {
    return dispatch => {
        return new Promise((resolve, reject) => {
            axios.get(`${Ressources.CoreUrl}/${Ressources.Calculatrice.api}/${Ressources.Calculatrice.stack}`).then(res => {
                dispatch(CalculatorSlice.actions.getAllStack(res.data));
                resolve(res.data);
            }).catch(function (error) {
                reject(error)
            })
        })
    }
};

export const getStackById = (id) => {
    return dispatch => {
        return new Promise((resolve, reject) => {
            axios.get(`${Ressources.CoreUrl}/${Ressources.Calculatrice.api}/${Ressources.Calculatrice.stack}/${id}`).then(res => {
                dispatch(CalculatorSlice.actions.getStackById(res.data));
                resolve(res.data);
            }).catch(function (error) {
                reject(error)
            })
        })
    }
};


export const addNewStack = (data) => {
    return dispatch => {
        return new Promise((resolve, reject) => {
            axiosWithHeaders.post(
                `${Ressources.CoreUrl}/${Ressources.Calculatrice.api}/${Ressources.Calculatrice.stack}`,
                data).then(res => {
                    dispatch(CalculatorSlice.actions.addNewStack(res.data))
                    resolve(res.data)
                }).catch(function (error) {
                    dispatch(CalculatorSlice.actions.setErrorMessage(error.response.data.message))
                })
        })
    }
};

export const empilerStack = (id, data) => {
    return dispatch => {
        return new Promise((resolve, reject) => {
            axiosWithHeaders.post(
                `${Ressources.CoreUrl}/${Ressources.Calculatrice.api}/${Ressources.Calculatrice.stack}/${id}`,
                data).then(res => {
                    dispatch(CalculatorSlice.actions.empilerStack(res.data))
                    resolve(res.data)
                }).catch(function (error) {
                    reject(error)
                })
        })
    }
};

export const depilerStack = (id) => {
    return dispatch => {
        return new Promise((resolve, reject) => {
            axiosWithHeaders.put(
                `${Ressources.CoreUrl}/${Ressources.Calculatrice.api}/${Ressources.Calculatrice.stack}/${id}?depiler=true`).then(res => {
                    dispatch(CalculatorSlice.actions.depilerStack(id))
                    resolve(id)
                }).catch(function (error) {
                    dispatch(CalculatorSlice.actions.setErrorMessage(error.response.data.description))
                })
        })
    }
};

export const apllyOperatorToStack = (id, operator) => {
    return dispatch => {
        return new Promise((resolve, reject) => {
            axiosWithHeaders.put(
                `${Ressources.CoreUrl}/${Ressources.Calculatrice.api}/${Ressources.Calculatrice.stack}/${id}?operator=${operator}`).then(res => {
                    dispatch(CalculatorSlice.actions.apllyOperatorToStack(res.data))
                    resolve(res.data)
                }).catch(function (error) {
                    dispatch(CalculatorSlice.actions.setErrorMessage(error.response.data.description))
                })
        })
    }
};

export const deleteStack = (id) => {
    return dispatch => {
        return new Promise((resolve, reject) => {
            axiosWithHeaders.delete(
                `${Ressources.CoreUrl}/${Ressources.Calculatrice.api}/${Ressources.Calculatrice.stack}/${id}`).then(res => {
                    dispatch(CalculatorSlice.actions.deleteStack(id))
                    resolve(id)
                }).catch(function (error) {
                    dispatch(CalculatorSlice.actions.setErrorMessage(error.response.data.description))
                })
        })
    }
};

export const setErrorMessage = (errorMessage) => {
    return dispatch => {
        return new Promise((resolve, reject) => {
            dispatch(CalculatorSlice.actions.setErrorMessage(errorMessage))
        })
    }
}
const initialState = {
    allOperator: null,
    allStack: [],
    selectedStack: null,
    newStack: null,
    errorMessage: null
}

export const CalculatorSlice = createSlice({
    name: 'Calculator',
    initialState,
    reducers: {
        getAllOperator: (state, action) => {
            state.allOperator = action.payload
        },
        getAllStack: (state, action) => {
            state.allStack = action.payload
        },
        getStackById: (state, action) => {
            state.selectedStack = action.payload
        },
        addNewStack: (state, action) => {
            state.allStack = state.allStack.concat(action.payload)
        },
        empilerStack: (state, action) => {
            state.selectedStack = action.payload
        },
        depilerStack: (state, action) => {
            state.selectedStack.stackElements = [];
            state.allStack = state.allStack
                .map(item => {
                    if (item.code !== action.payload) {
                        item.stackElements = [];
                    }
                    return item;
                }
                );
        },
        apllyOperatorToStack: (state, action) => {
            state.selectedStack = action.payload;
        },
        deleteStack: (state, action) => {
            state.allStack = state.allStack.filter(item => item.code !== action.payload);
            state.selectedStack.stackElements = [];
        },
        setErrorMessage: (state, action) => {
            state.errorMessage = action.payload;
        }
    }
})

export default CalculatorSlice.reducer
