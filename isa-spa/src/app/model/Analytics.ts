export type AnaliticalReport = {
  startDate: Date;
  endDate: Date;
  statisticsList: DailyStatistics[];
  totalRides: number;
  avgRides: number;
  totalKilometers: number;
  avgKilometers: number;
  totalMoney: number;
  avgMoney: number;
};

export type DailyStatistics = {
  date: Date;
  numOfRides: number;
  numOfKilometers: number;
  money: number;
};
