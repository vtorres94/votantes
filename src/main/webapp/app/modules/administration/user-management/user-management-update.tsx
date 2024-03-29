import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Label, Row, Col } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField, AvFeedback } from 'availity-reactstrap-validation';

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getUser, getRoles, updateUser, createUser, reset } from './user-management.reducer';
import { IRootState } from 'app/shared/reducers';
import { IUser } from 'app/shared/model/user.model';
import PasswordStrengthBar from 'app/shared/layout/password/password-strength-bar';

export interface IUserManagementUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ login: string }> {}

export interface IUserState {
  isNew: boolean;
  password: string;
  user: IUser;
}
export const UserManagementUpdate = (props: IUserManagementUpdateProps) => {
  const [state, setState] = useState<IUserState>({
    isNew: !props.match.params || !props.match.params.login,
    password: '',
    user: null,
  });

  useEffect(() => {
    if (state.isNew) {
      props.reset();
    } else {
      props.getUser(props.match.params.login);
    }
    props.getRoles();
    return () => {
      props.reset();
    };
  }, []);

  const handleClose = () => {
    props.history.push('/admin/user-management');
  };

  const saveUser = (event, values) => {
    if (state.isNew) {
      setState({
        ...state,
        user: {
          ...values,
          password: state.password,
          clienteId: 1,
        },
      });
      props.createUser(user);
    } else {
      setState({
        ...state,
        user: {
          ...values,
          password: state.password,
          clienteId: props.account.clienteId,
        },
      });
      props.updateUser(user);
      // props.updateUserPassword(state.user, props.account.clienteId);
    }
    handleClose();
  };

  const updatePassword = event => {
    setState({ ...state, password: event.target.value });
  };

  const isInvalid = false;
  const { user, loading, updating, roles } = props;

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h1>Create or edit a User</h1>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm onValidSubmit={saveUser}>
              {user.id ? (
                <AvGroup>
                  <Label for="id">ID</Label>
                  <AvField type="text" className="form-control" name="id" required readOnly value={user.id} />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label for="login">Login</Label>
                <AvField
                  type="text"
                  className="form-control"
                  name="login"
                  validate={{
                    required: {
                      value: true,
                      errorMessage: 'Your username is required.',
                    },
                    pattern: {
                      value: '^[a-zA-Z0-9!$&*+=?^_`{|}~.-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$|^[_.@A-Za-z0-9-]+$',
                      errorMessage: 'Your username is invalid.',
                    },
                    minLength: {
                      value: 1,
                      errorMessage: 'Your username is required to be at least 1 character.',
                    },
                    maxLength: {
                      value: 50,
                      errorMessage: 'Your username cannot be longer than 50 characters.',
                    },
                  }}
                  value={user.login}
                />
              </AvGroup>
              <AvGroup>
                <AvField
                  name="password"
                  label={'Password'}
                  placeholder={'Password'}
                  type="password"
                  onChange={updatePassword}
                  validate={{
                    required: { value: true, errorMessage: 'Este campo no puede estar vacío' },
                    minLength: { value: 6, errorMessage: 'Tiene que tener más de 6 caracteres' },
                    maxLength: { value: 50, errorMessage: 'No puede tener más de 50 caracteres' },
                  }}
                  value={user.password}
                />
              </AvGroup>
              <AvGroup>
                <PasswordStrengthBar password={state.password} />
                <AvField
                  name="secondPassword"
                  label={'Confirmar contraseña'}
                  placeholder={'Password'}
                  type="password"
                  validate={{
                    required: { value: true, errorMessage: 'Este campo no puede estar vacío' },
                    minLength: { value: 6, errorMessage: 'Tiene que tener más de 6 caracteres' },
                    maxLength: { value: 50, errorMessage: 'No puede tener más de 50 caracteres' },
                    match: { value: 'password', errorMessage: 'Las contraseñas no coinciden' },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label for="firstName">First Name</Label>
                <AvField
                  type="text"
                  className="form-control"
                  name="firstName"
                  validate={{
                    maxLength: {
                      value: 50,
                      errorMessage: 'This field cannot be longer than 50 characters.',
                    },
                  }}
                  value={user.firstName}
                />
              </AvGroup>
              <AvGroup>
                <Label for="lastName">Last Name</Label>
                <AvField
                  type="text"
                  className="form-control"
                  name="lastName"
                  validate={{
                    maxLength: {
                      value: 50,
                      errorMessage: 'This field cannot be longer than 50 characters.',
                    },
                  }}
                  value={user.lastName}
                />
                <AvFeedback>This field cannot be longer than 50 characters.</AvFeedback>
              </AvGroup>
              <AvGroup>
                <AvField
                  name="email"
                  label="Email"
                  placeholder={'Your email'}
                  type="email"
                  validate={{
                    required: {
                      value: true,
                      errorMessage: 'Your email is required.',
                    },
                    email: {
                      errorMessage: 'Your email is invalid.',
                    },
                    minLength: {
                      value: 5,
                      errorMessage: 'Your email is required to be at least 5 characters.',
                    },
                    maxLength: {
                      value: 254,
                      errorMessage: 'Your email cannot be longer than 50 characters.',
                    },
                  }}
                  value={user.email}
                />
              </AvGroup>
              <AvGroup check>
                <Label>
                  <AvInput type="checkbox" name="activated" value={user.activated} checked={user.activated} disabled={!user.id} /> Activated
                </Label>
              </AvGroup>
              <AvGroup>
                <Label for="authorities">Profiles</Label>
                <AvInput type="select" className="form-control" name="authorities" value={user.authorities} multiple>
                  {roles.map(role => (
                    <option value={role} key={role}>
                      {role}
                    </option>
                  ))}
                </AvInput>
              </AvGroup>
              <Button tag={Link} to="/admin/user-management" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">Back</span>
              </Button>
              &nbsp;
              <Button color="primary" type="submit" disabled={isInvalid || updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp; Save
              </Button>
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  user: storeState.userManagement.user,
  roles: storeState.userManagement.authorities,
  loading: storeState.userManagement.loading,
  updating: storeState.userManagement.updating,
  account: storeState.authentication.account,
});

const mapDispatchToProps = { getUser, getRoles, updateUser, createUser, reset };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(UserManagementUpdate);
