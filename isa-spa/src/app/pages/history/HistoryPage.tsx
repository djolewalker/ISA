import Table, { ColumnsType } from 'antd/es/table';
import { HeaderActions } from 'app/components/header-actions/HeaderActions';
import { IsaButton } from 'app/components/isa-button/IsaButton';
import { MainHeader } from 'app/components/main-header/MainHeader';
import { useLoader } from 'app/contexts/loader/loader-context-provider';
import { Ride } from 'app/model/Ride';
import { accountRideHistory, addToFavourites, removeFromFavourites, rideHistory } from 'app/service/ride.service';
import { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { StarOutlined, StarFilled } from '@ant-design/icons';

const tableColumns: (handleFavourite: (id: number, state: boolean) => void) => ColumnsType<Ride> = (
  handleFavourite
) => [
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
  },
  {
    dataIndex: 'favourite',
    key: 'actions',
    render: (favourite, record) => (
      <>
        <IsaButton
          className="mr-2"
          type="ghost"
          onClick={(event) => {
            handleFavourite(record.id, !favourite);
            event.stopPropagation();
          }}
          shape="circle"
          icon={favourite ? <StarFilled style={{ color: '#d4af37' }} /> : <StarOutlined style={{ color: '#d4af37' }} />}
        />
      </>
    )
  }
];

export const HistoryPage = () => {
  const navigate = useNavigate();
  const { activateLoader, deactivateLoader } = useLoader();
  const { userId } = useParams();
  const [rides, setRides] = useState<Ride[]>([]);
  const [showOnlyFavourites, setShowOnlyFavourites] = useState(false);

  const handleRideSelected = (rideId: number) => navigate(`/ride/history/${rideId}`);

  useEffect(() => {
    activateLoader();
    const getDataCallback = userId ? () => accountRideHistory(userId) : rideHistory;
    getDataCallback()
      .then((data) => {
        setRides(data);
        setShowOnlyFavourites(false);
      })
      .catch(console.error)
      .finally(deactivateLoader);
  }, [activateLoader, deactivateLoader, userId, setShowOnlyFavourites]);

  const handleFavourite = (id: number, state: boolean) => {
    const request = state ? addToFavourites : removeFromFavourites;
    request(id)
      .then(() => {
        setRides((currentRides) =>
          currentRides.map((ride) => {
            if (ride.id === id) {
              return { ...ride, favourite: state };
            }
            return ride;
          })
        );
      })
      .catch(console.error);
  };

  const handleShowOnlyFavourites = () => setShowOnlyFavourites((current) => !current);

  const ridesToDisplay = showOnlyFavourites ? rides.filter(({ favourite }) => favourite) : rides;

  return (
    <div className="d-flex flex-column flex-grow-1">
      <MainHeader>
        <HeaderActions />
      </MainHeader>
      <div className="my-3 d-flex flex-column flex-grow-1">
        <>
          <div className="mb-4 d-flex justify-content-between align-items-center">
            <h3 className="h3 m-0">Istorija vožnji: {userId ? `korisnik sa id-jem ${userId}` : ''} </h3>
            <IsaButton
              className="mx-4"
              type="ghost"
              onClick={handleShowOnlyFavourites}
              shape="circle"
              icon={
                showOnlyFavourites ? (
                  <StarFilled style={{ color: '#d4af37' }} />
                ) : (
                  <StarOutlined style={{ color: '#d4af37' }} />
                )
              }
            />
          </div>
          {rides?.length ? (
            <Table<Ride>
              rowKey={({ id }) => id}
              dataSource={ridesToDisplay}
              columns={tableColumns(handleFavourite)}
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
