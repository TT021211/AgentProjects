import { request } from '../../request';

type obj = { location: string; date: string };

export function getDeviceConsumptionValue(params: obj) {
  return request.get(`/ecm/ecmElectricityConsumption/getDeviceConsumptionValue24/${params.location}/${params.date}`);
}
type generalTable = {
  parentId: number;
  date: string;
};

export function getGeneralTableData(params: generalTable) {
  return request.get(
    `/ecm/ecmElectricityConsumption/getDeviceConsumptionValue24ByLine/${params.parentId}/${params.date}`
  );
}
