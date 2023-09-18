import { HeaderActions } from 'app/components/header-actions/HeaderActions';
import { MainHeader } from 'app/components/main-header/MainHeader';
import { DatePicker, Form, Input } from 'antd';
import { RangeValue } from 'rc-picker/lib/interface';
import { Dayjs } from 'dayjs';
import { useEffect, useMemo, useState } from 'react';
import { useLoader } from 'app/contexts/loader/loader-context-provider';
import { getReport, getUserReport } from 'app/service/analytics.service';
import { AnaliticalReport } from 'app/model/Analytics';

import { Chart as ChartJS, CategoryScale, LinearScale, BarElement, Legend, Tooltip } from 'chart.js';
import { Bar } from 'react-chartjs-2';
import FormItem from 'antd/es/form/FormItem';
import { useParams } from 'react-router-dom';

ChartJS.register(CategoryScale, LinearScale, BarElement, Tooltip, Legend);

export const options = {
  indexAxis: 'y' as const,
  elements: {
    bar: {
      borderWidth: 2
    }
  },
  responsive: true,
  plugins: {
    legend: {
      position: 'bottom' as const
    }
  }
};

const { RangePicker } = DatePicker;

export const AnalyticsPage = () => {
  const { activateTransparentLoader, deactivateLoader } = useLoader();
  const [dataRange, setDataRange] = useState<RangeValue<Dayjs>>();
  const [report, setReport] = useState<AnaliticalReport | undefined>();
  const { userId } = useParams();

  useEffect(() => {
    if (dataRange && dataRange[0] && dataRange[1]) {
      activateTransparentLoader();
      const request = userId
        ? getUserReport(parseInt(userId), dataRange[0].toDate(), dataRange[1].toDate())
        : getReport(dataRange[0].toDate(), dataRange[1].toDate());

      request.then(setReport).catch(console.error).finally(deactivateLoader);
    }
  }, [activateTransparentLoader, dataRange, deactivateLoader, userId]);

  const data = useMemo(() => {
    const labels = report?.statisticsList.map((day) => new Date(day.date).toLocaleDateString());
    return {
      labels,
      datasets: [
        {
          label: 'Broj vožnji',
          data: report?.statisticsList.map(({ numOfRides }) => numOfRides || 0),
          backgroundColor: 'rgb(53, 162, 235)'
        },
        {
          label: 'Pređeno kilometara',
          data: report?.statisticsList.map(({ numOfKilometers }) => numOfKilometers || 0),
          backgroundColor: 'rgb(75, 192, 192)'
        },
        {
          label: 'Potrošeno u dinarima',
          data: report?.statisticsList.map(({ money }) => money || 0),
          backgroundColor: 'rgb(255, 99, 132)'
        }
      ]
    };
  }, [report?.statisticsList]);

  const getDaysCount = () => {
    if (dataRange && dataRange[0] && dataRange[1]) {
      const daysCount = dataRange[1].diff(dataRange[0], 'days') + 1;
      return `${daysCount} dan/dana`;
    }
    return '';
  };

  return (
    <div className="d-flex flex-column flex-grow-1">
      <MainHeader>
        <HeaderActions />
      </MainHeader>
      <div className="my-3 d-flex flex-column flex-grow-1">
        <>
          <div className="mb-3 d-flex justify-content-between align-items-center">
            {userId ? (
              <h3 className="h3 m-0">Koirisnik sa id-jem: {userId}</h3>
            ) : (
              <h3 className="h3 m-0">Analitika za period: {getDaysCount()}</h3>
            )}
          </div>

          <RangePicker className="w-100 mb-3" value={dataRange} onChange={setDataRange} />

          {report && (
            <>
              <Form layout="vertical">
                <div className="d-flex mt-3">
                  <Form.Item className="flex-grow-1 mr-2" label="Ukupan broj vožnji za period">
                    <Input value={report?.totalRides || 0} readOnly />
                  </Form.Item>

                  <Form.Item className="flex-grow-1 ml-2" label="Prosečno vožnji po danu">
                    <Input value={report?.avgRides || 0} readOnly />
                  </Form.Item>
                </div>

                <div className="d-flex mt-3">
                  <Form.Item className="flex-grow-1 mr-2" label="Ukupno kilometara pređeno">
                    <Input value={report?.totalKilometers || 0} readOnly suffix="km" />
                  </Form.Item>

                  <Form.Item className="flex-grow-1 ml-2" label="Pređeno kilometara po danu">
                    <Input value={report?.avgKilometers || 0} readOnly suffix="km" />
                  </Form.Item>
                </div>

                <div className="d-flex mt-3">
                  <Form.Item className="flex-grow-1 mr-2" label="Ukupno plaćeno">
                    <Input value={report?.totalMoney || 0} readOnly suffix="RSD" />
                  </Form.Item>

                  <Form.Item className="flex-grow-1 ml-2" label="Plaćeno po danu">
                    <Input value={report?.avgMoney || 0} readOnly suffix="RSD" />
                  </Form.Item>
                </div>
              </Form>
              <div className="flex-grow-1 w-100 mt-2">
                <Bar options={options} data={data}></Bar>
              </div>
            </>
          )}
        </>
      </div>
    </div>
  );
};
