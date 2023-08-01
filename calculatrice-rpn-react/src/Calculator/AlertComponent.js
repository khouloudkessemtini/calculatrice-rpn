import React from 'react';
import Snackbar from '@mui/material/Snackbar';
import Alert from '@mui/material/Alert';

/* const Alert = (props) => {
  return <MuiAlert elevation={6} variant="filled" {...props} />;
}; */

export default function AlertComponent({ open, onClose, message }) {
  return (
    <Snackbar open={open} autoHideDuration={6000} onClose={onClose}>
      <Alert severity="error" onClose={onClose} >
        {message}
      </Alert>
    </Snackbar>
  );
}
