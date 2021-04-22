import { ICCliente } from 'app/shared/model/c-cliente.model';
import { ICDefensorVoto } from 'app/shared/model/c-defensor-voto.model';

export interface ICVotante {
  id?: number;
  nombreCompleto?: string;
  nombre?: string;
  segundoNombre?: string | null;
  apellidoPaterno?: string;
  segundoMaterno?: string | null;
  edad?: number | null;
  telefono?: string | null;
  claveElector?: string | null;
  seccionElectoral?: number | null;
  calle?: string | null;
  numExt?: string | null;
  colonia?: string | null;
  cp?: number | null;
  municipio?: string | null;
  estado?: string | null;
  estatus?: number;
  borrado?: number;
  cliente?: ICCliente | null;
  defensorVoto?: ICDefensorVoto | null;
}

export const defaultValue: Readonly<ICVotante> = {};
