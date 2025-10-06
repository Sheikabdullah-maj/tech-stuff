import React from "react";
import {
    useReactTable,
    getCoreRowModel,
    flexRender,
    createColumnHelper,
    getFilteredRowModel,
    getPaginationRowModel,
    getSortedRowModel

} from "@tanstack/react-table";
import { fetchEmployeesAccessCardTrackerData } from "../service/APIConsumer";
import { useQuery } from "@tanstack/react-query";
import Box from "@mui/material/Box";
import TextField from '@mui/material/TextField';
import Typography from "@mui/material/Typography";
import { getDateInLocalDate } from "../utils/Utility";

const columnHelper = createColumnHelper();

const columns = [
    columnHelper.accessor("id", {
        header: "ID",
        cell: info => info.getValue(),
    }),
    columnHelper.accessor("name", {
        header: "Name",
        cell: info => info.getValue(),
    }),
    columnHelper.accessor("deskNumber", {
        header: "Desk #",
        cell: info => info.getValue(),
    }),
    columnHelper.accessor("tapIn", {
        header: "Tap In",
        cell: info => info.getValue(),
    }),
    columnHelper.accessor("tapOut", {
        header: "Tap Out",
        cell: info => info.getValue(),
    })
];

function WatchDogGrid() {
    const { data = [], isLoading, isError } = useQuery({
        queryKey: ["employees"],
        queryFn: fetchEmployeesAccessCardTrackerData,
        refetchInterval: 10000,
    });

    const [globalFilter, setGlobalFilter] = React.useState("");
    const [pagination, setPagination] = React.useState({ pageIndex: 0, pageSize: 10 })

    const table = useReactTable({
        data,
        columns,
        state: { globalFilter, pagination },
        onGlobalFilterChange: setGlobalFilter,
        onPaginationChange: setPagination,
        getCoreRowModel: getCoreRowModel(),
        getFilteredRowModel: getFilteredRowModel(),
        getPaginationRowModel: getPaginationRowModel(),
        getSortedRowModel: getSortedRowModel(),
        initialState: { pagination: { pageIndex: 0, pageSize: 10 } },
        globalFilterFn: (row, columnId, filterValue) => {
            const search = filterValue.toLowerCase();

            // 🔍 Check only selected columns
            const searchableColumns = ["id", "name", "deskNumber"];
            return searchableColumns.some((col) =>
                String(row.getValue(col)).toLowerCase().includes(search)
            );
        }
    });

    const todaysDate = getDateInLocalDate();

    if (isLoading) return <p>Loading...</p>;
    if (isError) return <p>Error fetching data</p>;

    return (
        <Box sx={{ width: '100%', mb: 2, padding: 15 }}>
            <Typography variant="h6" gutterBottom>
                Date: {todaysDate}
            </Typography>
            <TextField
                id="filled-search"
                label="Try Search By Id, Name, Desk Number"
                type="search"
                variant="filled"
                value={globalFilter ?? ""}
                onChange={(e) => setGlobalFilter(e.target.value)}
            />
            <table className="border-collapse border border-gray-400 w-full">
                <thead>
                    {table.getHeaderGroups().map(headerGroup => (
                        <tr key={headerGroup.id}>
                            {headerGroup.headers.map(header => (
                                <th
                                    key={header.id}
                                    className="border border-gray-300 p-2 bg-gray-100 text-left"
                                >
                                    {flexRender(header.column.columnDef.header, header.getContext())}
                                </th>
                            ))}
                        </tr>
                    ))}
                </thead>
                <tbody>
                    {table.getRowModel().rows.map(row => (
                        <tr key={row.id} className={row.original.tapOut == null || row.original.tapOut === '' ? 'bg-green-100' : ''}>
                            {row.getVisibleCells().map(cell => (
                                <td key={cell.id} className="border border-gray-300 p-2">
                                    {flexRender(cell.column.columnDef.cell, cell.getContext())}
                                </td>
                            ))}
                        </tr>
                    ))}
                </tbody>
            </table>
            <div className="flex items-center justify-between mt-4 gap-4">
                <div className="flex items-center gap-2">
                    <button
                        className="px-3 py-1 rounded border disabled:opacity-50"
                        onClick={() => table.setPageIndex(0)}
                        disabled={!table.getCanPreviousPage()}
                    >
                        {'<<'}
                    </button>
                    <button
                        className="px-3 py-1 rounded border disabled:opacity-50"
                        onClick={() => table.previousPage()}
                        disabled={!table.getCanPreviousPage()}
                    >
                        Prev
                    </button>
                    <button
                        className="px-3 py-1 rounded border disabled:opacity-50"
                        onClick={() => table.nextPage()}
                        disabled={!table.getCanNextPage()}
                    >
                        Next
                    </button>
                    <button
                        className="px-3 py-1 rounded border disabled:opacity-50"
                        onClick={() => table.setPageIndex(table.getPageCount() - 1)}
                        disabled={!table.getCanNextPage()}
                    >
                        {'>>'}
                    </button>
                </div>


                <div className="flex items-center gap-2">
                    <span className="text-sm">Page</span>
                    <strong>
                        {table.getState().pagination.pageIndex + 1} of {table.getPageCount()}
                    </strong>


                    <label className="flex items-center gap-2">
                        | Go to page:
                        <input
                            type="number"
                            defaultValue={table.getState().pagination.pageIndex + 1}
                            onChange={e => {
                                const page = e.target.value ? Number(e.target.value) - 1 : 0
                                table.setPageIndex(page)
                            }}
                            className="w-16 px-2 py-1 border rounded"
                            min={1}
                            max={table.getPageCount()}
                        />
                    </label>


                    <select
                        value={table.getState().pagination.pageSize}
                        onChange={e => {
                            table.setPageSize(Number(e.target.value))
                        }}
                        className="px-2 py-1 border rounded"
                    >
                        {[5, 10, 20, 50].map(size => (
                            <option key={size} value={size}>
                                Show {size}
                            </option>
                        ))}
                    </select>
                </div>
            </div>
        </Box>
    );

}

export default WatchDogGrid;