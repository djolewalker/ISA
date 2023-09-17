import Table, { ColumnsType } from 'antd/es/table';
import { HeaderActions } from 'app/components/header-actions/HeaderActions';
import { MainHeader } from 'app/components/main-header/MainHeader';
import { useLoader } from 'app/contexts/loader/loader-context-provider';
import { Ride } from 'app/model/Ride';
import { accountRideHistory, rideHistory } from 'app/service/ride.service';
import { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';

const tableColumns: ColumnsType<Ride> = [
  {
    title: 'Vreme starta',
    dataIndex: 'startTime',
    key: 'startTime',
    render: (value) => new Date(value).toLocaleString()
  },
  {
    title: 'Vreme završetka',
    dataIndex: 'finishTime',
    key: 'finishTime',
    render: (value) => (value ? new Date(value).toLocaleString() : 'Vožnja u toku')
  },
  {
    title: 'Cena',
    dataIndex: 'totalPrice',
    key: 'totalPrice',
    render: (value) => `${value} RSD`
  }
];

export const HistoryPage = () => {
  const navigate = useNavigate();
  const { activateLoader, deactivateLoader } = useLoader();
  const { userId } = useParams();
  const [rides, setRides] = useState<Ride[]>([]);

  const handleRideSelected = (rideId: number) => navigate(`/ride/history/${rideId}`);

  useEffect(() => {
    activateLoader();
    const getDataCallback = userId ? () => accountRideHistory(userId) : rideHistory;
    getDataCallback().then(setRides).catch(console.error).finally(deactivateLoader);
  }, [activateLoader, deactivateLoader, userId]);

  return (
    <div className="d-flex flex-column flex-grow-1">
      <MainHeader>
        <HeaderActions />
      </MainHeader>
      <div className="my-3 d-flex flex-column flex-grow-1">
        <>
          <div className="mb-4 d-flex justify-content-between align-items-center">
            <h3 className="h3 m-0">Istorija vožnji: {userId ? `korisnik sa id-jem ${userId}` : ''}</h3>
          </div>
          {Boolean(rides?.length) ? (
            <Table<Ride>
              rowKey={({ id }) => id}
              dataSource={rides}
              columns={tableColumns}
              pagination={false}
              onRow={({ id }) => ({ onClick: () => handleRideSelected(id) })}
            />
          ) : (
            <div className="d-flex flex-grow-1 align-items-center">
              <h2 className="h2 mb-4 text-center">Odabrani korisnik nije učestvovao u vožnjama!</h2>
            </div>
          )}
        </>
      </div>
    </div>
  );
};
