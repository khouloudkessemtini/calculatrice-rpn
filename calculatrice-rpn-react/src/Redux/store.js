import { configureStore } from '@reduxjs/toolkit'
import CalculatorSlice from '../Calculator/CalculatorSlice'


// Automatically adds the thunk middleware and the Redux DevTools extension
const store = configureStore({
  // Automatically calls `combineReducers`
  reducer: {
    CalculatorSlice: CalculatorSlice
  }
})

export default store;