import { request } from '../../request';

const BASE_URL = '/ecm/ecmElectricityConsumption';

// 获取设备电能耗列表
export const getMachineEnergyList = (params: any) => {
  return request.get(`${BASE_URL}/list`, { params });
};

// 获取设备电能耗详情

export const getMachineEnergyDetail = (code: string, date: string, type = 'date') => {
  let url: string = '';
  switch (type) {
    case 'date':
      url = 'calculateHourlyConsumption';
      break;
    case 'month':
      url = 'calculateDailyConsumption';
      break;
    case 'year':
      url = 'calculateMonthlyConsumption';
      break;
    default:
      url = 'calculateHourlyConsumption';
  }
  console.log(url, type, '----');
  return request.get(`${BASE_URL}/${url}/${code}/${date}`);
};
