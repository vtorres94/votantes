import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ICAgenda, defaultValue } from 'app/shared/model/c-agenda.model';

export const ACTION_TYPES = {
  FETCH_CAGENDA_LIST: 'cAgenda/FETCH_CAGENDA_LIST',
  FETCH_CAGENDA: 'cAgenda/FETCH_CAGENDA',
  CREATE_CAGENDA: 'cAgenda/CREATE_CAGENDA',
  UPDATE_CAGENDA: 'cAgenda/UPDATE_CAGENDA',
  PARTIAL_UPDATE_CAGENDA: 'cAgenda/PARTIAL_UPDATE_CAGENDA',
  DELETE_CAGENDA: 'cAgenda/DELETE_CAGENDA',
  RESET: 'cAgenda/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ICAgenda>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type CAgendaState = Readonly<typeof initialState>;

// Reducer

export default (state: CAgendaState = initialState, action): CAgendaState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_CAGENDA_LIST):
    case REQUEST(ACTION_TYPES.FETCH_CAGENDA):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_CAGENDA):
    case REQUEST(ACTION_TYPES.UPDATE_CAGENDA):
    case REQUEST(ACTION_TYPES.DELETE_CAGENDA):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_CAGENDA):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_CAGENDA_LIST):
    case FAILURE(ACTION_TYPES.FETCH_CAGENDA):
    case FAILURE(ACTION_TYPES.CREATE_CAGENDA):
    case FAILURE(ACTION_TYPES.UPDATE_CAGENDA):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_CAGENDA):
    case FAILURE(ACTION_TYPES.DELETE_CAGENDA):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_CAGENDA_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_CAGENDA):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_CAGENDA):
    case SUCCESS(ACTION_TYPES.UPDATE_CAGENDA):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_CAGENDA):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_CAGENDA):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {},
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState,
      };
    default:
      return state;
  }
};

const apiUrl = 'api/c-agenda';

// Actions

export const getEntities: ICrudGetAllAction<ICAgenda> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_CAGENDA_LIST,
    payload: axios.get<ICAgenda>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<ICAgenda> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_CAGENDA,
    payload: axios.get<ICAgenda>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<ICAgenda> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_CAGENDA,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ICAgenda> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_CAGENDA,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<ICAgenda> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_CAGENDA,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<ICAgenda> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_CAGENDA,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
