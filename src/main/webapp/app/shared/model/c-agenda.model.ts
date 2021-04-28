import dayjs from 'dayjs';

export interface ICAgenda {
  id?: number;
  direccion?: string | null;
  fecha?: string | null;
  hora?: string | null;
  encargado?: string | null;
  estatus?: number | null;
}

export const defaultValue: Readonly<ICAgenda> = {};
