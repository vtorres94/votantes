import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction, ICrudSearchAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ICVotante, defaultValue } from 'app/shared/model/c-votante.model';

export const ACTION_TYPES = {
  FETCH_CVOTANTE_LIST: 'cVotante/FETCH_CVOTANTE_LIST',
  FETCH_CVOTANTE: 'cVotante/FETCH_CVOTANTE',
  CREATE_CVOTANTE: 'cVotante/CREATE_CVOTANTE',
  UPDATE_CVOTANTE: 'cVotante/UPDATE_CVOTANTE',
  PARTIAL_UPDATE_CVOTANTE: 'cVotante/PARTIAL_UPDATE_CVOTANTE',
  DELETE_CVOTANTE: 'cVotante/DELETE_CVOTANTE',
  RESET: 'cVotante/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ICVotante>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type CVotanteState = Readonly<typeof initialState>;

// Reducer

export default (state: CVotanteState = initialState, action): CVotanteState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_CVOTANTE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_CVOTANTE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_CVOTANTE):
    case REQUEST(ACTION_TYPES.UPDATE_CVOTANTE):
    case REQUEST(ACTION_TYPES.DELETE_CVOTANTE):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_CVOTANTE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_CVOTANTE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_CVOTANTE):
    case FAILURE(ACTION_TYPES.CREATE_CVOTANTE):
    case FAILURE(ACTION_TYPES.UPDATE_CVOTANTE):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_CVOTANTE):
    case FAILURE(ACTION_TYPES.DELETE_CVOTANTE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_CVOTANTE_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_CVOTANTE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_CVOTANTE):
    case SUCCESS(ACTION_TYPES.UPDATE_CVOTANTE):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_CVOTANTE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_CVOTANTE):
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

const apiUrl = 'api/c-votantes';

// Actions

export const getEntities: ICrudSearchAction<ICVotante> = (search, page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?borrado.equals=0&page=${page}&size=${size}&sort=${sort}${search}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_CVOTANTE_LIST,
    payload: axios.get<ICVotante>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<ICVotante> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_CVOTANTE,
    payload: axios.get<ICVotante>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<ICVotante> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_CVOTANTE,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ICVotante> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_CVOTANTE,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<ICVotante> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_CVOTANTE,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<ICVotante> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_CVOTANTE,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
