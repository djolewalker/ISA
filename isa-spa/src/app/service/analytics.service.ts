import { AnaliticalReport } from 'app/model/Analytics';
import { postRequest } from 'app/service/base.service';

const CONTROLLER = 'report';

export const getReport = async (startDate: Date, endDate: Date) =>
  postRequest(CONTROLLER, { startDate, endDate }).then(({ data }) => data as AnaliticalReport);

export const getUserReport = async (userId: number, startDate: Date, endDate: Date) =>
  postRequest(CONTROLLER, { userId, startDate, endDate }).then(({ data }) => data as AnaliticalReport);
