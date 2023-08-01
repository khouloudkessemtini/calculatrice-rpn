import React, { useEffect, useState } from 'react'
import { useDispatch, useSelector } from "react-redux";

import { Button, Select, MenuItem, TextField, InputLabel } from '@mui/material';

import AlertComponent from './AlertComponent';
import {
  getAllOperator,
  getAllStack,
  getStackById,
  empilerStack,
  apllyOperatorToStack,
  depilerStack,
  addNewStack,
  deleteStack,
  setErrorMessage
} from './CalculatorSlice';


const Calculator = () => {

  const dispatch = useDispatch();
  const allOperator = useSelector(state => state.CalculatorSlice.allOperator);
  const allStack = useSelector(state => state.CalculatorSlice.allStack);
  const selectedStack = useSelector(state => state.CalculatorSlice.selectedStack);
  const errorMessage = useSelector(state => state.CalculatorSlice.errorMessage);

  const [valueCalculator, setValueCalculator] = useState('0');
  const [memo, setMemo] = useState('');
  const [designation, setDesignation] = useState('');

  let allOperatorCopie = allOperator;

  useEffect(() => {
    dispatch(getAllStack())
    dispatch(getAllOperator())
  }, [dispatch])

  const handleSelectStack = (event) => {
    setValueCalculator('0');
    dispatch(getStackById(event.target.value)).then((result) => {
      setDesignation(result.designation);
      setMemo(result.memo);
    });
  };

  const handleDesignationChange = (event) => {
    setDesignation(event.target.value);
  };

  
  const handleMemoChange = (event) => {
    setMemo(event.target.value);
  };
  const updateCalculator = (value) => {
    if (value === "C") {
      setValueCalculator('0');
    } else {
      valueCalculator == '0' ?
        setValueCalculator(value.toString()) :
        setValueCalculator(valueCalculator.toString() + value.toString())
    }
  };
  const handleClickCreation = () => {
    let data = {};
    data.designation = designation;
    data.memo = memo;
    dispatch(addNewStack(data));
  };
  const handleClickEmpiler = () => {
    if (!selectedStack) {
      dispatch(setErrorMessage('SVP Sélection une pile parmi piles existantes.'));
    } else {
      if (valueCalculator == 0) {
        dispatch(setErrorMessage('Attention, empiler la pile avec des valeurs zéro ne sert à rien.'));
      } else {
        dispatch(empilerStack(selectedStack.code, valueCalculator))
          .then(() => setValueCalculator('0'));
      }
    }
  };
  const handleClickDepiler = () => {
    if (!selectedStack)
      dispatch(setErrorMessage('SVP Sélection une pile parmi piles existantes.'));
    else
      dispatch(depilerStack(selectedStack.code))
  };
  const handleClickDelete = () => {
    if (!selectedStack)
      dispatch(setErrorMessage('SVP Sélection une pile parmi piles existantes.'));
    else {
      dispatch(deleteStack(selectedStack.code))
      setDesignation('');
      setMemo('');
    }
  };
  const handleClickOperateur = (operant) => {
    if (!selectedStack)
      dispatch(setErrorMessage('SVP Sélection une pile parmi piles existantes.'));
    else {
      setValueCalculator('0');
      dispatch(apllyOperatorToStack(selectedStack.code, operant));
    }
  };


  const createDigits = () => {
    const btnDigits = [];
    for (let i = 1; i < 10; i++) {
      btnDigits.push(<Button
        key={i}
        onClick={() => {
          updateCalculator(i);
        }}
      >
        {i}
      </Button>)
    }
    btnDigits.push(<Button
      key={0}
      onClick={() => {
        updateCalculator(0);
      }}
    >{0}
    </Button>)
    btnDigits.push(<Button style={{ color: '#b3261e' }}
      key={"C"}
      onClick={() => {
        updateCalculator("C");
      }}
    >C
    </Button>)
    return btnDigits;
  }

  const createBtnOperator = () => {
    const btnOperator = [];
    allOperatorCopie && Object.keys(allOperatorCopie).map((key) => (
      btnOperator.push(<Button
        key={key}
        onClick={() => handleClickOperateur(key)}
      >{allOperatorCopie[key]}
      </Button>)
    ))
    return btnOperator;
  }

  return (
    <div>
      <div className='alert'>
        <AlertComponent open={errorMessage !== null} onClose={() => dispatch(setErrorMessage(null))} message={errorMessage} />
      </div>
      <div className="App">
        <div className="info">
          <div className='margin'>
            <InputLabel>Sélection une pile: </InputLabel>
            <Select
              fullWidth
              multiline
              onChange={(event) => handleSelectStack(event)}
            >
              {allStack && allStack.map((item) =>
                <MenuItem key={item.code}
                  value={item.code}>
                  {item.designation}
                </MenuItem>
              )}
            </Select>
          </div>
          <div className='margin'>
            <TextField
              label="Designation"
              variant="outlined"
              fullWidth
              multiline
              value={designation}
              onChange={handleDesignationChange}
            />
          </div>
          <div>
            <TextField
              label="Memo"
              variant="outlined"
              fullWidth
              multiline
              value={memo}
              onChange={handleMemoChange}
            />
          </div>
        </div>
        <div className="calculator">

          <div className="display">
            {selectedStack
              && selectedStack.stackElements
              && selectedStack.stackElements.map((item) =>
                <span lassName="colorBlack"> {item.value}</span>
              )}

            <span> {valueCalculator}</span>
          </div>
          <div className='operatorsAnddigits'>
            <div className="block-btn">
              {createDigits()}
            </div>
            <div className="block-btn">
              {createBtnOperator()}
            </div>

          </div>
        </div>

        <div className="action">
          <Button className='success'
            onClick={() => { handleClickCreation() }}>Création</Button>
          <Button className='warning'
            onClick={() => { handleClickDepiler() }}>Remise à zéro</Button>
          <Button className='blue'
            onClick={() => { handleClickEmpiler(); }}>Empiler</Button>
          <Button className='danger'
            onClick={() => { handleClickDelete() }}>Supprimer</Button>
        </div>
      </div>
    </div>
  )

}
export default Calculator; 
