import dayjs from 'dayjs';

export interface ICCliente {
  id?: number;
  claveCliente?: string | null;
  cliente?: string | null;
  anioElectoral?: number | null;
  idUsuarioCreacion?: number;
  fechaCreacion?: string;
  idUsuarioActualizacion?: number | null;
  fechaActualizacion?: string | null;
  notas?: string | null;
  estatus?: number;
  borrado?: number;
}

export const defaultValue: Readonly<ICCliente> = {};
